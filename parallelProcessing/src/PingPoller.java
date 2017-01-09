import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class PingPoller implements Callable<Response>
{
    private String ipAddress;
    private int index;

    public void setIpAddress(String ipAddress,int index){
        this.ipAddress=ipAddress;
        this.index=index;
    }


    @Override
    public Response call() throws Exception
    {
        //System.out.println("Ping Poller Starts...");
        Boolean pingResult = false;
        try
        {
            InetAddress inet = InetAddress.getByName(ipAddress);
            System.out.println("Sending Ping Request to " + ipAddress);
            pingResult = inet.isReachable(5000); //Timeout = 5000 milli seconds

        }
        catch (UnknownHostException e)
        {
            System.err.println("Host does not exists");
        }
        catch (IOException e)
        {
            System.err.println("Error in reaching the Host");
        }
        return new Response(index,pingResult);
    }
}
