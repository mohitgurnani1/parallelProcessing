/**
 * Created by Mohit on 1/8/2017.
 */
public class Response {
    int index;

    public Response(int index, boolean pingResponse){
        this.index=index;
        this.pingResponse=pingResponse;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isPingResponse() {
        return pingResponse;
    }

    public void setPingResponse(boolean pingResponse) {
        this.pingResponse = pingResponse;
    }

    boolean pingResponse;
}
