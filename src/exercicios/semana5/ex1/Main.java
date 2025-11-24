package exercicios.semana5.ex1;

import java.util.List;

public class Main {
	static final int NUM_THREADS=10;
	static final int STRING_LENGTH=1024001;
	static final int CHUNK_LENGTH=1024;
	static final String STRING_TO_BE_FOUND="rui";

	public static void main(String[] args) {
		SearcherThread[] threads=new SearcherThread[NUM_THREADS];

		// Gera um 'documento de texto' e carrega-o no repositorio
		RandomString rs=new RandomString(STRING_LENGTH);
		String text = rs.nextString();
		TextRepository textRepository=new TextRepository(text, STRING_TO_BE_FOUND, CHUNK_LENGTH);

		// Cria um vetor de threads para procurar o texto
		for(int i=0; i!=NUM_THREADS;i++){
			threads[i]=new SearcherThread(textRepository);
			threads[i].start();
		}

		// Para versao 2
				try {
					List<TextChunk> results= textRepository.getAllMatches();
					if(results!=null)
						for(TextChunk chunk:results)
							System.out.println("Encontrado em "+chunk);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
