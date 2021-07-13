package com.tuanpm.RCTMqtt;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class AlpnSSLSocketFactory extends SSLSocketFactory {
    protected SSLSocketFactory parent;
    protected String[] alpn;

    public AlpnSSLSocketFactory(@NonNull SSLSocketFactory parent, @NonNull String[] alpn) {
        this.parent = parent;
        this.alpn = alpn;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return parent.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return parent.getSupportedCipherSuites();
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

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        Socket s = parent.createSocket(socket, host, port, autoClose);
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
