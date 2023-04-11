package main.java.audiocards;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.io.FileWriter;

public class Scraper {

    private final String url;
    Document doc;
    private final Voice voice;
    List<String> questions;
    File file; 


    public Scraper(String url){
        this.url = url;
        voice = new VoiceManager.getInstance().getVoice("kevin16");
        file = new File("QuizletAudio.wav");
    }

    public void getData(){
        doc = Jsoup.connect(url).get();
        Element terms = doc.getElementsByClass("SetPageTerms-termWrapper");
        questions = terms.eachText();
    }

    public void createAudio() throws IOException {
        voice.allocate();
        FileOutputStream outputStream = new FileOutputStream(file, true);
        for (int i = 0; i < questions.size(); i++) {
            String q = questions.get(i);
            voice.speak(q);
            voice.save(outputStream);
        }
        voice.deallocate();
        outputStream.close();
    }
    
}
