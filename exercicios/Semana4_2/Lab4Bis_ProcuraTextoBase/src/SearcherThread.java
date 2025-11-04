class SearcherThread extends Thread {
    private TextRepository textRepository;

    public SearcherThread(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @Override
    public void run() {
        while(!interrupted()) {
            TextChunk stringToSearch;
            try {
                stringToSearch = textRepository.getChunk();
            } catch (InterruptedException e) {
                System.out.println("SearcherThread estava á espera de novos pedaços e foi interrompida");
                return;
            }
            int foundPos =stringToSearch.text.indexOf(stringToSearch.stringToBeFound);

            while(foundPos != -1) {
                stringToSearch.addFoundPos(foundPos);
                //System.out.println(stringToSearch);
                //System.out.println("Encontrei a string na posicao " + foundPos );
                foundPos=stringToSearch.text.indexOf(stringToSearch.stringToBeFound, foundPos + 1);
            }

            textRepository.submitResult(stringToSearch);
        }
    }
}