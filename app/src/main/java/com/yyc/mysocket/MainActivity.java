package com.yyc.mysocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_link)
    TextView tvLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_link, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                break;
            case R.id.tv_link:
                break;
            case R.id.tv_cancel:
                break;
        }
    }

    private void connect() {

        EchoWebSocketListener listener = new EchoWebSocketListener();
        Request request = new Request.Builder()
                .url("ws://echo.websocket.org")
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();
    }

    private void output(final String content) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                text.setText(text.getText().toString() + content + "\n");
                Log.e("aa","content:" + content);
            }
        });
    }

    public final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            webSocket.send("hello world");
            webSocket.send("welcome");
            webSocket.send(ByteString.decodeHex("adef"));
            webSocket.close(1000, "再见");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output("onMessage: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("onMessage byteString: " + bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            output("onClosing: " + code + "/" + reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            output("onClosed: " + code + "/" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("onFailure: " + t.getMessage());
        }
    }
}




