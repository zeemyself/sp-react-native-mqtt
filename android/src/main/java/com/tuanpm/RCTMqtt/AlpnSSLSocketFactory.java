package com.tuanpm.RCTMqtt;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

public class AlpnSSLSocketFactory extends SocketFactory {
    protected SocketFactory parent;
    protected String[] alpn;

    public AlpnSSLSocketFactory(@NonNull SocketFactory parent, @NonNull String[] alpn) {
        this.parent = parent;
        this.alpn = alpn;
    }

    @Override
    public Socket createSocket() throws IOException {
        Socket s = parent.createSocket();
        setSocketOption(s);
        return s;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        Socket s = parent.createSocket(host, port);
        setSocketOption(s);
        return s;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        Socket s = parent.createSocket(host, port, localHost, localPort);
        setSocketOption(s);
        return s;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        Socket s = parent.createSocket(host, port);
        setSocketOption(s);
        return s;
    }

    @Override
    public Socket createSocket(InetAddress host, int port, InetAddress localHost, int localPort) throws IOException {
        Socket s = parent.createSocket(host, port, localHost, localPort);
        setSocketOption(s);
        return s;
    }

    protected void setSocketOption(Socket socket) {
        SSLSocket ssl = (SSLSocket) socket;
        SSLParameters parameters = ssl.getSSLParameters();
        parameters.setApplicationProtocols(alpn);
        ssl.setSSLParameters(parameters);
    }
}
