package com.stackroute.bookservice.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.bookservice.Model.Book;
import com.stackroute.bookservice.Model.BookNotes;
import com.stackroute.bookservice.Model.MqBook;
import com.stackroute.bookservice.Model.Note;
import com.stackroute.bookservice.Service.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.IOException;
import java.util.*;
/*import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;*/

//@CrossOrigin(origins="*")
@RestController
@RequestMapping("api/v1")
public class Controller {

    private BookService bookService;
    private BookContent extractor;
    private MessageService message;
    private DownloadService downloadService;
    private LocalFileSaveService localFileSaveService;

    @Autowired
    public Controller(BookService bookService, BookContent extractor, HttpServletRequest request, MessageService message, DownloadService downloadService, LocalFileSaveService localFileSaveService) {
        this.bookService = bookService;
        this.extractor = extractor;
        this.localFileSaveService = localFileSaveService;
        this.message = message;
        this.downloadService = downloadService;
    }


    @Value("${spring.rabbitmq.routingkey}")
    private String BOOK_ROUTING_KEY;


    @PostMapping("/book/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile[] file, @RequestParam("details") String details) throws Exception {
        MultipartFile pdffile = file[0];
        MultipartFile imgFile = file[1];

        Book extractedContent = extractor.contentExtractor(pdffile);  //Extracting the content of Pdf using Tika
        String bookUrl = localFileSaveService.saveFile(pdffile); // Uploading Pdf to S3 Bucket
        String imageUrl = localFileSaveService.saveFile(imgFile); // Uploading Pdf to S3 Bucket

        Book readValue = new ObjectMapper().readValue(details,extractedContent.getClass());  //Mapping the object from json
		BookNotes bookNotes = new BookNotes(readValue.getBookTitle());
		bookService.save(bookNotes);
        Book bookDetails = new Book(
                readValue.getBookTitle(), readValue.getAuthorName(), readValue.getDescription(),
                extractedContent.getBookContent(), bookUrl, readValue.getTotalPage(),
                readValue.getPublisher(), readValue.getGenre(), imageUrl
        );
        MqBook mqBook = new MqBook(readValue.getBookTitle(),readValue.getAuthorName(),readValue.getDescription(),bookUrl,0,readValue.getTotalPage(),readValue.getPublisher(),0,imageUrl, UUID.randomUUID().toString(),readValue.getGenre());


        message.sendMessage(BOOK_ROUTING_KEY,mqBook);
        String status = bookService.saveDocumentInput(bookDetails); //Indexing in elastic search

        String message = "File Upload Successful";
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + message + "\"}");
    }

//    @PostMapping("/rabbitmqCall")
//    public ResponseEntity<String> rabbitMQCall(@RequestBody MqBook mqBook){
//        MqBook mqBook = new MqBook("Title","Author","Desc","bookUrl",768,230,"Publisher name",125,"image Url","isbn number");
//        message.sendMessage(BOOK_ROUTING_KEY,mqBook);
//        return new ResponseEntity<String>("Published Details",HttpStatus.OK);
//    }



    @GetMapping("/search/{queries}")
	public List<Book> BookSearch(@PathVariable List<String> queries){
        System.out.println("\n\n\nInside Book Service.Ths is what we have got from book query :  "+queries);
        String text = "";
        for (String query: queries) {
            text = text.concat(query);
            text = text.concat(" ");
        }
		System.out.println("\n\nReturnning back to bookquery service");
        return bookService.findBook(text);
    }

    @PostMapping("/audio")
	public void textSpeech(@RequestBody String link){
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();
            synthesizer.speakPlainText(bookService.contentForAudio(link),null);
            synthesizer.waitEngineState(
                    Synthesizer.QUEUE_EMPTY);

        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/pauseaudio")
	public void pause(){
        try{
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            synthesizer.pause();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @GetMapping("/resumeaudio")
	public void resume(){
        try{
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            synthesizer.resume();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @GetMapping("/stopaudio")
	public void stop(){
        try{
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            synthesizer.cancel();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    @PostMapping("/download")
	public ResponseEntity<?> downloadBook(@RequestBody Map<String,String> data) {
		String bookUrl=data.get("bookUrl");
		String bookTitle=data.get("bookTitle");
        //  String url= "https://imran728.files.wordpress.com/2015/08/headfirstjava2ndedition.pdf";
       // String url2="https://tika-elas-s3.s3-ap-southeast-1.amazonaws.com/2020-11-25T09%3A48%3A20.097213_BookTest.pdf";

        try {

//            List<String> typeList = null;
            bookUrl= bookUrl.trim();
//            typeList = Arrays.asList(url.split("_"));
//            downloadService.downloadUsingStream(url,"C:/Users/intel/Downloads/"+typeList.get(1));
//            downloadService.downloadUsingStream(url,"$HOME/Downloads/"+typeList.get(1));
            downloadService.downloadUsingStream(bookUrl,"~/Downloads/"+bookTitle+".pdf");
            return  new ResponseEntity<String>("book downloaded", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return  new ResponseEntity<String>("file not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(value = "bookTitle") String bookTitle,
                                                  @RequestParam(value = "userEmail") String userEmail) {

        if(bookService.getAllNotes(bookTitle,userEmail)==null||
                bookService.getAllNotes(bookTitle,userEmail).isEmpty())
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(bookService.getAllNotes(bookTitle,userEmail),HttpStatus.OK);

    }

    @PostMapping("/addNotes")
    public ResponseEntity<String> updateNotes(@RequestBody Note note,
                                              @RequestParam(value = "bookTitle") String bookTitle ){

	    try {

            List<Note> noteList = new ArrayList<>();
            List<Note> notes = bookService.getBookNote(bookTitle).get().getNotes();
            if (notes == null)
                noteList.add(note);
            else {
                noteList = notes;
                noteList.add(note);
            }
            BookNotes bookNotes = new BookNotes(bookTitle, noteList);
            bookService.save(bookNotes);
            return new ResponseEntity<>("Notes added Successfully", HttpStatus.OK);
        }
        catch (Exception e){
	        return new ResponseEntity<String>("Title Already Exist",HttpStatus.CONFLICT);
        }

    }


    @PostMapping("/saveBook")
    public ResponseEntity<String> saveBook(@RequestBody String bookTitle)
    {
        BookNotes bookNotes = new BookNotes(bookTitle);
        bookService.save(bookNotes);
        return new ResponseEntity<>("Book Added Successfully", HttpStatus.OK);
    }

    @PostMapping("/deleteNotes")
    public ResponseEntity<String> deleteNotes(@RequestBody String bookTitle,
                                              @RequestBody String noteTitle) {
        try {
            List<Note> notes = bookService.getBookNote(bookTitle).get().getNotes();
            for (int i = (notes.size()-1); i > -1; i--) {
                if (notes.get(i).getNoteTitle().equals(noteTitle))
                    notes.remove(i);
            }
            BookNotes bookNotes = new BookNotes(bookTitle, notes);
            bookService.save(bookNotes);
            return new ResponseEntity<>("Notes Deleted Successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/bookfilter")
    public ResponseEntity<?> bookFilters(@RequestParam(value="genre",required=false) String genre, @RequestParam(value="author",required=false) String author, @RequestParam(value="bookTitle",required=false) String bookTitle)
    {
        List<String> filters=new ArrayList<>();
        filters.add(genre);filters.add(author);filters.add(bookTitle);
        String input = genre.concat(" ").concat(author).concat(" ").concat(bookTitle);
        if(!input.isEmpty()){
            return  new ResponseEntity<List<Book>>(bookService.findBook(input), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("error",HttpStatus.NOT_FOUND);
        }
    }

    //Method Added To Remove NLP Service
    @GetMapping(value = "/filter")
    public ResponseEntity<?> nlpFilter(@RequestParam("input") final String input, @RequestParam("type") String type) {
        if(!input.isEmpty()){
            return  new ResponseEntity<List<Book>>(bookService.findBook(input), HttpStatus.OK);
        } else{
            return new ResponseEntity<String>("error",HttpStatus.NOT_FOUND);
        }
    }
}
