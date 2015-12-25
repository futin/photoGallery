package com.example.futin.importimages.RestService.loaders;

/**
 * Created by Futin on 12/22/2015.
 */
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    /*
        Simple method for getting input stream and writing it to output stream, in this case,
        stream of image bytes
    */
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
