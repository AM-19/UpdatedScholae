# cors_http_server.py
from http.server import SimpleHTTPRequestHandler, HTTPServer

class CORSRequestHandler(SimpleHTTPRequestHandler):
    def end_headers(self):
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET')
        self.send_header('Access-Control-Allow-Headers', 'Content-Type')
        SimpleHTTPRequestHandler.end_headers(self)

if __name__ == '__main__':
    server_address = ('', 8892)
    httpd = HTTPServer(server_address, CORSRequestHandler)
    print('Starting HTTP server with CORS support on port 8892...')
    httpd.serve_forever()
