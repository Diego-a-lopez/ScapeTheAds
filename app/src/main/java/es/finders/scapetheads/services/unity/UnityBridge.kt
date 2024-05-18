package es.finders.scapetheads.services.unity

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

class UnityBridge : Service() {

    private val TAG = "UnityBridge"
    private val PORT = 8080
    private var mode = JSONObject().apply {
        put("gamemode", "infinite")
    }
    private val _eventFlow = MutableStateFlow<String?>(null)
    val eventFlow = _eventFlow.asStateFlow()

    fun getMode(): JSONObject {
        Log.d(TAG, "Retrieved mode")
        return mode
    }

    fun setInfinite() {
        setMode(JSONObject().apply {
            put("gamemode", "infinite")
        })
    }

    fun setLevel(level: Int) {
        setMode(JSONObject().apply {
            put("gamemode", "level")
            put("gamemode", level)
        })
    }

    fun setMode(newMode: JSONObject) {
        Log.d(TAG, "Set mode")
        this.mode = newMode
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    init {
        _eventFlow.value = null
        Thread { startServer() }.start()
    }

    private fun startServer() {
        try {
            val serverSocket = ServerSocket(PORT)
            Log.d(TAG, "Server listening on port $PORT")

            while (true) {
                val clientSocket = serverSocket.accept()
                Log.d(TAG, "Client connected: ${clientSocket.inetAddress}")

                // Send data to the client
                sendData(clientSocket, mode.toString())

                // Receive data from the client
                val receivedData = receiveData(clientSocket)
                Log.d(TAG, "Received data: $receivedData")

                handleData(receivedData)
                clientSocket.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun sendData(socket: Socket, data: String) {
        val outputStream: OutputStream = socket.getOutputStream()
        outputStream.write(data.toByteArray())
        outputStream.flush()
    }

    private fun receiveData(socket: Socket): String {
        val inputStream: InputStream = socket.getInputStream()
        val buffer = ByteArray(1024)
        val bytesRead = inputStream.read(buffer)
        return String(buffer, 0, bytesRead)
    }

    private fun handleData(data: String) {
        _eventFlow.value = data
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service onDestroy")
    }
}