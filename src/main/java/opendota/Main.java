package opendota;

import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
    
public class Main {

    public static void main(String[] args) throws Exception {
        // args[0] input directory
        // args[1] output directory

        String inputDir = args[0];
        String outDir = args[1];

        File dir = new File(inputDir);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                InputStream inputStream = new FileInputStream(child);

                child.getName();

                String outFileName = outDir + "/" + child.getName().substring(0, child.getName().length()-4) +".json";

                File outFile = new File(outFileName);
                OutputStream outputStream = new FileOutputStream(outFile);

                if (!outFile.exists()) {
                    outFile.createNewFile();
                }

                new Parse(inputStream, outputStream);

                outputStream.close();
            }
        } else {
            System.out.println("Something broke((");
        }


    }
    
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, 0);
            InputStream is = t.getRequestBody();
            OutputStream os = t.getResponseBody();
            try {
            	new Parse(is, os);
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            }
            os.close();
        }
    }
}
