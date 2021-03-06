import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Download {
   private static boolean isRedirected( Map<String, List<String>> header ) {
      for( String hv : header.get( null )) {
         if(   hv.contains( " 301 " )
            || hv.contains( " 302 " )) return true;
      }
      return false;
   }
   public static void main( String[] args ) throws Throwable
   {
      String link =
         "http://github.com/downloads/TheHolyWaffle/ChampionHelper/" +
         "ChampionHelper-4.jar";
      String            fileName = "ChampionHelper-4.jar";
      URL               url  = new URL( link );
      HttpURLConnection http = (HttpURLConnection)url.openConnection();
      Map< String, List< String >> header = http.getHeaderFields();
      while( isRedirected( header )) {
         link = header.get( "Location" ).get( 0 );
         url    = new URL( link );
         http   = (HttpURLConnection)url.openConnection();
         header = http.getHeaderFields();
      }
      InputStream  input  = http.getInputStream();
      byte[]       buffer = new byte[4096];
      int          n      = -1;
      OutputStream output = new FileOutputStream( new File( "C:\\test\\"+fileName ));
      while ((n = input.read(buffer)) != -1) {
         output.write( buffer, 0, n );
      }
      output.close();
   }
}