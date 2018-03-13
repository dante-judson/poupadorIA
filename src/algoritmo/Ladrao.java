package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Ladrao extends ProgramaLadrao {

	private final int PARADO = 0;
	private final int CIMA = 1;
	private final int BAIXO = 2;
	private final int DIREITA = 3;
	private final int ESQUERDA = 4;
	
	private final int VISAOCIMA = 7;
	private final int VISAOBAIXO = 16;
	private final int VISAODIREITA = 12;
	private final int VISAOESQUERDA = 11;
	
	private int[][] matriz;

	Ladrao(){
		this.matriz = new int[30][30];
	}


	public int acao() {
		Point posicaoAtual = sensor.getPosicao();
		this.matriz[(int) posicaoAtual.getY() - 1][(int) posicaoAtual.getX() - 1]++;

		List<Sucessor> sucessores = getSucessor(posicaoAtual);
		
		persepcao(sucessores);
		
		return tomarDecisao(sucessores);
	}


	private List<Sucessor> getSucessor(Point posicaoAtual){

		List<Sucessor> sucessores = new ArrayList<Sucessor>();

		int x = (int) (posicaoAtual.getX() +1);

		if(x < 30){
			sucessores.add(new Sucessor(new Point(x,(int) posicaoAtual.getY()), DIREITA));
		}

		x = (int) posicaoAtual.getX() - 1;

		if(x > 0){
			sucessores.add(new Sucessor(new Point(x,(int) posicaoAtual.getY()),ESQUERDA));
		}

		int y = (int) posicaoAtual.getY() +1;

		if( y < 30 ){
			sucessores.add(new Sucessor(new Point((int) posicaoAtual.getX(), y),BAIXO));
		}

		y = (int) posicaoAtual.getY() - 1;

		if(y > 0){
			sucessores.add(new Sucessor(new Point((int) posicaoAtual.getX(), y), CIMA));
		}

		return sucessores;
	}
	
	private void persepcao(List<Sucessor> sucessores) {
		int[] visao = sensor.getVisaoIdentificacao();
		printaMatriz();
		for (Sucessor s : sucessores) {
			if(s.acaoGeradora == CIMA) {
				if(visao[VISAOCIMA] != 0) {
					this.matriz[(int) s.posicao.getY() - 1][(int) s.posicao.getX() - 1] += 9;
				}
			}
			
			if(s.acaoGeradora == DIREITA) {
				if(visao[VISAODIREITA] != 0) {
					this.matriz[(int) s.posicao.getY() - 1][(int) s.posicao.getX() - 1] += 9;
				}
			}
			
			if(s.acaoGeradora == ESQUERDA) {
				if(visao[VISAOESQUERDA] != 0) {
					this.matriz[(int) s.posicao.getY() - 1][(int) s.posicao.getX() - 1] += 9;
				}
			}
			
			if(s.acaoGeradora == BAIXO) {
				if(visao[VISAOBAIXO] != 0) {
					this.matriz[(int) s.posicao.getY() - 1][(int) s.posicao.getX() - 1] += 9;
				}
			}
		}
		printaMatriz();
	}
	
	private int tomarDecisao(List<Sucessor> sucessores) {
		int menorEsforco = Integer.MAX_VALUE;
		int decisao = PARADO;
		
		for (Sucessor s : sucessores) {
			Point p = s.posicao;
			
			int esforco = utilidade(s);
			if(esforco < menorEsforco) {
				menorEsforco = esforco;
				decisao = s.acaoGeradora;
			}
		}
		
		return decisao;
	}
	
	
	private void printaMatriz() {
		System.out.println("MATRIZ UTILIDADE ------------------------------------");
		
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 30; j++) {
				System.out.print("["+this.matriz[i][j]+"]");
			}
			System.out.println("");
		}
	}
	
	private int utilidade(Sucessor sucessor) {
		
		int[] visao = sensor.getVisaoIdentificacao();
		
		if(sucessor.acaoGeradora == CIMA) {
			if(visao[VISAOCIMA] >= 100 && visao[VISAOCIMA] < 200) {
				return (this.matriz[sucessor.posicao.y - 1][sucessor.posicao.x -1]) * (-2);
			}
		}
		
		if(sucessor.acaoGeradora == BAIXO) {
			if(visao[VISAOBAIXO] >= 100 && visao[VISAOBAIXO] < 200) {
				return (this.matriz[sucessor.posicao.y - 1][sucessor.posicao.x -1]) * (-2);
			}
		}
		
		if(sucessor.acaoGeradora == ESQUERDA) {
			if(visao[VISAOESQUERDA] >= 100 && visao[VISAOESQUERDA] < 200) {
				return (this.matriz[sucessor.posicao.y - 1][sucessor.posicao.x -1]) * (-2);
			}
		}
		
		if(sucessor.acaoGeradora == DIREITA) {
			if(visao[VISAODIREITA] >= 100 && visao[VISAODIREITA] < 200) {
				return (this.matriz[sucessor.posicao.y - 1][sucessor.posicao.x -1]) * (-2);
			}
		}
		
		return (this.matriz[sucessor.posicao.y - 1][sucessor.posicao.x -1]);
	}
	
	private class Sucessor{
		
		public Point posicao;
		
		public int acaoGeradora;
		
		Sucessor(Point posicao, int acaoGeradora){
			this.posicao = posicao;
			this.acaoGeradora = acaoGeradora;
		}
		
	}

}


