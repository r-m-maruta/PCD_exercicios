package exercicios.semana4_2.ex_por_ver;

import java.util.ArrayList;
import java.util.List;

public class TextRepository {
    private List<TextChunk> chunks=new ArrayList<>();
    private List<TextChunk> foundChunks=new ArrayList<>();
    private int numChunks;
    private int numReceivedResults;

    public TextRepository(String text, String stringToBeFound, int chunkSize) {
        for(int i=0; i<text.length(); i+=chunkSize) {
            chunks.add(new TextChunk(text.substring(i, (int)Math.min(i+chunkSize,text.length())),
                    i, stringToBeFound));
            numChunks++;
        }
    }

    public synchronized TextChunk getChunk() throws InterruptedException {
        while(chunks.size()== 0) {
            wait();
        }
        return chunks.removeFirst();
    }

    public synchronized void submitResult(TextChunk chunk) {
        numReceivedResults++;
        if(chunk.getFoundPos().size() > 1)
            foundChunks.add(chunk);
        if(numChunks == numReceivedResults)
            notifyAll();

    }

    public synchronized List<TextChunk> getAllMatches() throws InterruptedException {
        while(numChunks != numReceivedResults)
            wait();

        return foundChunks;
    }
}