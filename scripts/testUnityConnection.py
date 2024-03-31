import json
import socketserver
import signal
import sys

class MyTCPHandler(socketserver.BaseRequestHandler):
    def handle(self):
        # Send data to the client
        data_to_send = {
            "gamemode": "infinite"
        }
        self.request.sendall(json.dumps(data_to_send).encode())

        # Receive data from the client
        received_data = self.request.recv(1024).decode()
        print(f"Received data: {received_data}")

def signal_handler(sig, frame):
    print("\nServer stopped by user.")
    sys.exit(0)

if __name__ == "__main__":
    HOST, PORT = "localhost", 8080

    # Set up signal handler for Ctrl+C
    signal.signal(signal.SIGINT, signal_handler)

    with socketserver.TCPServer((HOST, PORT), MyTCPHandler) as server:
        print(f"Server listening on {HOST}:{PORT}")
        server.serve_forever()