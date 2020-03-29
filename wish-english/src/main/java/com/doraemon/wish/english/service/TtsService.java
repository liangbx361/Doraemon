package com.doraemon.wish.english.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import okio.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Service
public class TtsService {

    private static final String hostUrl = "https://tts-api.xfyun.cn/v2/tts"; //http url 不支持解析 ws/wss schema
    private static final String apiKey = "4da544290aecb47d30b9326202e37465";
    private static final String apiSecret = "5bfafde8123db302d1e3edeaba3992f7";
    private static final String appid = "5e0972a0";

    @Value("${study-en.voice}")
    private String voiceLocation;

    public void tts(String text, Callback callback) throws Exception {
        Gson json = new Gson();

        // 构建鉴权url
        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
        OkHttpClient client = new OkHttpClient.Builder().build();
        //将url中的 schema http://和https://分别替换为ws:// 和 wss://
        String url = authUrl.replace("http://", "ws://").replace("https://", "wss://");
        Request request = new Request.Builder().url(url).build();
        // 存放音频的文件
        File voiceFile = new File(voiceLocation, text + ".wav");
        if (voiceFile.exists()) {
            callback.onSuccess(voiceFile);
            return;
        }

        voiceFile.createNewFile();
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(voiceFile));
        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //发送数据
                JsonObject frame = new JsonObject();
                JsonObject business = new JsonObject();
                JsonObject common = new JsonObject();
                JsonObject data = new JsonObject();
                // 填充common
                common.addProperty("app_id", appid);
                //填充business
                business.addProperty("ent", "intp65_en");
                business.addProperty("aue", "raw");
                business.addProperty("tte", "gb2312");
                business.addProperty("vcn", "xiaoyan");
                business.addProperty("pitch", 50);
                business.addProperty("auf", "audio/L16;rate=16000");

                //填充data
                data.addProperty("status", 2);
                data.addProperty("text", Base64.getEncoder().encodeToString(text.getBytes()));
                data.addProperty("encoding", "");
                //填充frame
                frame.add("common", common);
                frame.add("business", business);
                frame.add("data", data);
                webSocket.send(frame.toString());
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                //处理返回数据
                System.out.println("receive=>" + text);
                ResponseData resp = null;
                try {
                    resp = json.fromJson(text, ResponseData.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (resp != null) {
                    if (resp.getCode() != 0) {
                        System.out.println("error=>" + resp.getMessage() + " sid=" + resp.getSid());
                        return;
                    }
                    if (resp.getData() != null) {
                        String result = resp.getData().audio;
                        byte[] audio = Base64.getDecoder().decode(result);
                        try {
                            // 转wave参考 https://stackoverflow.com/questions/37281430/how-to-convert-pcm-file-to-wav-or-mp3
                            // WAVE header
                            // see https://blog.csdn.net/yejia1280/article/details/70216699
                            // wav 转 mp3 https://blog.csdn.net/qq_33129625/article/details/78550691
                            writeString(outputStream, "RIFF"); // 0 4 chunk id
                            writeInt(outputStream, 36 + audio.length); // 4 4 波形块的大小
                            writeString(outputStream, "WAVE"); // 8 4
                            writeString(outputStream, "fmt "); // 12 4
                            writeInt(outputStream, 16); // 16 4 过滤字节 一般为0x10
                            writeShort(outputStream, (short) 1); // 20 2 audio format (1 = PCM)
                            writeShort(outputStream, (short) 1); // 22 2 number of channels
                            writeInt(outputStream, 16000); // 24 4 sample rate
                            writeInt(outputStream, 16000 * 2); // 28 4 波形数据传输速率
                            writeShort(outputStream, (short) 2); // 32 2 DATA数据块长度，字节数＝SampleRate＊NumChannels＊BitPerSample／8
                            writeShort(outputStream, (short) 16); // 34 2 PCM位宽
                            writeString(outputStream, "data"); // 36 4 DATA总数据长度字节＝NumSample＊NumChannels＊BitPerSample／8
                            writeInt(outputStream, audio.length); // 40 4 DATA数据块
                            outputStream.write(audio);
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (resp.getData().status == 2) {
                            // todo  resp.data.status ==2 说明数据全部返回完毕，可以关闭连接，释放资源
                            System.out.println("session end ");
                            webSocket.close(1000, "");

                            callback.onSuccess(voiceFile);
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("socket closing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("socket closed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                System.out.println("connection failed");
            }
        });
    }

    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
            append("date: ").append(date).append("\n").//
            append("GET ").append(url.getPath()).append(" HTTP/1.1");
        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        String authorization = String.format("hmac username=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().//
            addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).//
            addQueryParameter("date", date).//
            addQueryParameter("host", url.getHost()).//
            build();
        return httpUrl.toString();
    }

    public static class ResponseData {
        private int code;
        private String message;
        private String sid;
        private Data data;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return this.message;
        }

        public String getSid() {
            return sid;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Data {
        private int status;  //标志音频是否返回结束  status=1，表示后续还有音频返回，status=2表示所有的音频已经返回
        private String audio;  //返回的音频，base64 编码
        private String ced;  // 合成进度
        private String spell;  //音频的拼音标注
    }

    interface Callback {

        void onSuccess(File file);
    }

    private void writeInt(final DataOutputStream output, final int value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
        output.write(value >> 16);
        output.write(value >> 24);
    }

    private void writeShort(final DataOutputStream output, final short value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
    }

    private void writeString(final DataOutputStream output, final String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            output.write(value.charAt(i));
            System.out.println(value.charAt(i));
        }
    }
}
