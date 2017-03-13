package hu.sovaroq.game.core.service.game;

/**
 * Created by balazs_horvath on 3/13/2017.
 */
public class LUAJSHIT {
    private String prevShit;

    public LUAJSHIT(){
        prevShit = "EMPTY_SHIT";
    }

    public void printShit(String shit){
        this.prevShit = shit;
        System.out.println("HERES SHOME SHIT: " + shit);
    }
    public void printPreviousShit(){
        System.out.println("HERES SHOME SHIT: " + prevShit);
    }
}