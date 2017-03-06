package com.brunomeurer.myflappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brunomeurer.myflappybird.componentes.Moeda;
import com.brunomeurer.myflappybird.componentes.MoedaPremio;
import com.brunomeurer.myflappybird.componentes.TuboInferior;
import com.brunomeurer.myflappybird.componentes.TuboSuperior;
import com.brunomeurer.myflappybird.personagem.Passaro;

import java.util.Random;

public class MyFlappyBird extends ApplicationAdapter {

	//desenha na tela
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	//texturas do game
	private Texture fundo;
	private Texture fimDoJogo;

	//propriedades do dispositivo
	private float larguraDoDispositivo;
	private float alturaDoDispositivo;

	//Personagem principal
	private Passaro passaro;


	//propriedades da fisica do game
	private float variacao;
	private float velocidadeQueda;

	//propriedades do cenário

	private TuboSuperior tuboSuperior;
	private TuboInferior tuboInferior;
	private Moeda moeda;
	private MoedaPremio premio;
	private float posicaoCanoHorizontal;
	private int posicaoFundoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandomica;
	private Random numeroRandomico;
	private int estadoDoJogo; //0-não iniciado, 1-iniciado, 2-game over
	private boolean marcouPonto;
	private OrthographicCamera camera;
	private Viewport viewport;
	private final float VIRTUAL_WIDTH = 600;
	private final float VIRTUAL_HEIGHT = 1024;

	//propriedades score
	private BitmapFont fonte;
	private int pontuacao;
	private int moedas;
	private int record;

	//propriedades gameover
	private BitmapFont mensagem;

	private Music musicaDeFundo;
	private Music efeitoMoeda;
	private Music efeitoPerdeu;


	@Override
	public void create () {
		//inicia game
		Gdx.app.log("Create", "Inicializado o jogo");

		//inicializa variavel para desenho
		batch = new SpriteBatch(); //renderizar tela
		shapeRenderer = new ShapeRenderer();//desenhar colisores

		fundo = new Texture("fundo.png");
		fimDoJogo = new Texture("game_over.png");

		fonte = new BitmapFont();
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(6);

		mensagem = new BitmapFont();
		mensagem.setColor(Color.WHITE);
		mensagem.getData().setScale(3);

		//configurações da camera
		camera = new OrthographicCamera();
		camera.position.set(VIRTUAL_WIDTH/2,VIRTUAL_HEIGHT/2,0f);
		viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

		larguraDoDispositivo = VIRTUAL_WIDTH;
		alturaDoDispositivo = VIRTUAL_HEIGHT;

		passaro = new Passaro(larguraDoDispositivo, alturaDoDispositivo);
		tuboSuperior = new TuboSuperior();
		tuboInferior = new TuboInferior();
		moeda = new Moeda();
		premio = new MoedaPremio();

		variacao = 0;
		velocidadeQueda = 0;
		estadoDoJogo = 0;
		espacoEntreCanos = 400;
		posicaoCanoHorizontal = larguraDoDispositivo;
		posicaoFundoHorizontal = 0;

		numeroRandomico = new Random();

		Gdx.app.log("Tamanho da tela", "Tamanho X>" + larguraDoDispositivo);
		Gdx.app.log("Tamanho da tela", "Tamanho Y>" + alturaDoDispositivo);

		//Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/musica_de_fundo.mp3"));
		musicaDeFundo = Gdx.audio.newMusic(Gdx.files.internal("data/musica_de_fundo.mp3"));
		musicaDeFundo.setVolume(0.2f);
		musicaDeFundo.setLooping(true);

		efeitoMoeda = Gdx.audio.newMusic(Gdx.files.internal("data/moeda.mp3"));
		efeitoMoeda.setVolume(0.5f);
		efeitoPerdeu = Gdx.audio.newMusic(Gdx.files.internal("data/perdeu.mp3"));
		efeitoPerdeu.setVolume(0.0f);
		efeitoPerdeu.setLooping(false);

	}

	@Override
	public void render () {

		ajustesDeCamperaEPerformance();

		//variação para animação do passaro
		variacao += deltaTime *4;
		if(variacao>=3){
			variacao = 0;
		}

		switch (estadoDoJogo){
			case 0:{
				if(Gdx.input.justTouched()){
					velocidadeQueda = -20;
					estadoDoJogo = 1;

					musicaDeFundo.play();
				}
				break;
			}
			case 1:
			case 2:{

				velocidadeQueda++;

				if(passaro.getyPassaro()>0) {
					passaro.setyPassaro(passaro.getyPassaro() - velocidadeQueda);
				}else{
					estadoDoJogo = 2;
					musicaDeFundo.pause();
					efeitoPerdeu.play();
				}

				if(estadoDoJogo == 1){
					posicaoCanoHorizontal -= deltaTime * 400;
					posicaoFundoHorizontal -= deltaTime * 100;

					//verifica se o cano saiu inteiramente da tela
					if(posicaoCanoHorizontal< -tuboSuperior.getWidth() ){
						marcouPonto = false;
						alturaEntreCanosRandomica = numeroRandomico.nextInt((int)alturaDoDispositivo - 200) - ((alturaDoDispositivo - 200) / 2);
						posicaoCanoHorizontal = larguraDoDispositivo;
						moeda.setPegou(false);


						premio.setX(posicaoCanoHorizontal + numeroRandomico.nextInt((int)larguraDoDispositivo));
						premio.setY(numeroRandomico.nextInt((int)alturaDoDispositivo));

					}

					if(posicaoFundoHorizontal + larguraDoDispositivo < 0 )
					{
						posicaoFundoHorizontal = 0;
					}

					if(Gdx.input.justTouched()){
						velocidadeQueda = -20;
					}

					if(posicaoCanoHorizontal < 150 && !marcouPonto){
						pontuacao++;
						marcouPonto = true;
					}
				}else if(estadoDoJogo == 2){

					if(Gdx.input.justTouched()){
						estadoDoJogo = 1;
						pontuacao = 0;

						passaro.setyPassaro (larguraDoDispositivo/2);
						passaro.setyPassaro (alturaDoDispositivo/2);

						posicaoCanoHorizontal = larguraDoDispositivo;

						velocidadeQueda = -20;
						moeda.setPegou(false);
						efeitoPerdeu.pause();
						musicaDeFundo.play();
					}
				}
				break;
			}

			default:{
				Gdx.app.log("ERROR","ESTADO DO JOGO NÃO EXISTE=>"+ estadoDoJogo);
			}
		}

		atualizaPosicaoColisores();

		renderizarEmTelaObjetos();

		desenhaColisores();
	}

	public void ajustesDeCamperaEPerformance(){
		camera.update();
		//Limpar os frames anteriores
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		deltaTime = Gdx.graphics.getDeltaTime();
	}

	public void renderizarEmTelaObjetos(){
		batch.begin();

		batch.setProjectionMatrix(camera.combined);

		//desenha plano de fundo
		batch.draw(fundo, posicaoFundoHorizontal, 0, larguraDoDispositivo, alturaDoDispositivo);
		batch.draw(fundo, posicaoFundoHorizontal + larguraDoDispositivo - 5, 0, larguraDoDispositivo, alturaDoDispositivo);

		//desenha canos
		batch.draw(tuboSuperior.getTuboSuperior(), posicaoCanoHorizontal,
				alturaDoDispositivo/2 + espacoEntreCanos/2 + alturaEntreCanosRandomica,
				tuboSuperior.getWidth(),
				tuboSuperior.getHeight());
		batch.draw(tuboInferior.getTuboInferior(), posicaoCanoHorizontal,
				alturaDoDispositivo/2 - (tuboInferior.getHeight()) - espacoEntreCanos/2 + alturaEntreCanosRandomica,
				tuboInferior.getWidth(),
				tuboInferior.getHeight());

		if(!moeda.isPegou()) {

			batch.draw(moeda.getMoeda(), posicaoCanoHorizontal + tuboSuperior.getWidth() / 2 - 50 / 2,
					alturaDoDispositivo / 2 + alturaEntreCanosRandomica - 50 / 2, 51, 50);



				batch.draw(premio.getMoeda(), premio.getX(),
						premio.getY(), 51, 50);


		}

		Sprite sprite = new Sprite(passaro.getFramePassaro((int)variacao));
		sprite.setPosition(150,
				passaro.getyPassaro());
		sprite.setSize(passaro.getFramePassaro((int)variacao).getWidth(), passaro.getFramePassaro((int)variacao).getHeight());

		if(velocidadeQueda<0){
			sprite.setRotation(20);
		}else if(velocidadeQueda==0 && velocidadeQueda<=5){
			sprite.setRotation(0);
		}else if(velocidadeQueda>5){
			sprite.setRotation(-20);
		}

		sprite.draw(batch);

		fonte.draw(batch, String.valueOf(pontuacao), larguraDoDispositivo /2 -25, alturaDoDispositivo - 50);
		mensagem.draw(batch, "x" + String.valueOf(moedas), 50, 45);
		batch.draw(moeda.getMoeda(), 8, 10, 40, 40);

		if(estadoDoJogo == 0 || estadoDoJogo == 2){
			mensagem.draw(batch, String.valueOf("Toque para iniciar."), larguraDoDispositivo /2 - 200 , alturaDoDispositivo / 2 - fimDoJogo.getHeight()/2);

		}

		if(estadoDoJogo == 2){
			batch.draw(fimDoJogo, larguraDoDispositivo/2-fimDoJogo.getWidth()/2, alturaDoDispositivo/2-fimDoJogo.getHeight()/2);

		}

		batch.end();
	}

	public void desenhaColisores(){

		if(variacao<2) {

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.circle(passaro.getColisorPassaro().x, passaro.getColisorPassaro().y, passaro.getColisorPassaro().radius);
			shapeRenderer.rect(tuboInferior.getColisorTuboInferior().x, tuboInferior.getColisorTuboInferior().y, tuboInferior.getColisorTuboInferior().width, tuboInferior.getColisorTuboInferior().height);
			shapeRenderer.rect(tuboSuperior.getColisorTuboSuperior().x, tuboSuperior.getColisorTuboSuperior().y, tuboSuperior.getColisorTuboSuperior().width, tuboSuperior.getColisorTuboSuperior().height);
			shapeRenderer.circle(moeda.getColisorMoeda().x, moeda.getColisorMoeda().y, moeda.getColisorMoeda().radius);
			shapeRenderer.circle(premio.getColisorMoeda().x, premio.getColisorMoeda().y, premio.getColisorMoeda().radius);
			shapeRenderer.setColor(Color.RED);

			shapeRenderer.end();
		}
	}

	public void atualizaPosicaoColisores(){
		moeda.updateColisor(posicaoCanoHorizontal + tuboSuperior.getWidth() / 2 /*51 / 2*/,alturaDoDispositivo / 2 + alturaEntreCanosRandomica);


		premio.updateColisor(premio.getX(), premio.getY());

		tuboSuperior.updateColisor(posicaoCanoHorizontal,
				alturaDoDispositivo/2 + espacoEntreCanos/2 + alturaEntreCanosRandomica);

		tuboInferior.updateColisor(posicaoCanoHorizontal,
				alturaDoDispositivo/2 - (tuboInferior.getHeight()) - espacoEntreCanos/2 + alturaEntreCanosRandomica);

		passaro.updateColisor();
		testaColisoes();
	}

	public void testaColisoes(){
		//teste de colisão
		if(Intersector.overlaps(passaro.getColisorPassaro(), tuboInferior.getColisorTuboInferior()) || Intersector.overlaps(passaro.getColisorPassaro(), tuboSuperior.getColisorTuboSuperior())){
			estadoDoJogo = 2;
			musicaDeFundo.stop();
			efeitoPerdeu.play();
			if(record < pontuacao){
				record = pontuacao;
			}
		}

		if(!moeda.isPegou() && Intersector.overlaps(moeda.getColisorMoeda(), passaro.getColisorPassaro())){
			moeda.setPegou(true);
			moedas += 1;
			efeitoMoeda.play();
		}
	}

	@Override
	public void resize(int width, int height){
		viewport.update(width, height);
	}
}
