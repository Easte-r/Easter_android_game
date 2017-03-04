package com.rzd.dafeiji;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;


public class DafeijiView extends SurfaceView implements SurfaceHolder.Callback,Runnable,android.view.View.OnTouchListener{
	private Bitmap my1,my2,my3,my4;
	private Bitmap diji1,diji2,diji3,diji4;
	private Bitmap bg;
	private Bitmap baozha1,baozha2,baozha4,baozha5,baozha6;
	private Bitmap zidan;
	private Bitmap erjihuancun;
	
	private WindowManager windowManager;
	private int display_w;
	private int display_h;
	private int youxijieshu = 0;
	private ArrayList<GameImage> gameImages = new ArrayList<GameImage>();
	private ArrayList<Zidan> zidans = new ArrayList<Zidan>();
	private ArrayList<DirenImage> direnImages = new ArrayList<DirenImage>();//���˷ɻ����б�
	private FeijiImage feijiImage = null;
	public  DafeijiView(Context context) {
		super(context);
		getHolder().addCallback(this);
		this.setOnTouchListener(this);//�¼�ע��
	}
	private void init(){
		my1 = BitmapFactory.decodeResource(getResources(), R.drawable.ziji1);
		my2 = BitmapFactory.decodeResource(getResources(), R.drawable.ziji2);
		my3 = BitmapFactory.decodeResource(getResources(), R.drawable.ziji3);
		my4 = BitmapFactory.decodeResource(getResources(), R.drawable.ziji4);
		diji1 = BitmapFactory.decodeResource(getResources(), R.drawable.diji1);
		diji2 = BitmapFactory.decodeResource(getResources(), R.drawable.diji2);
		diji3 = BitmapFactory.decodeResource(getResources(), R.drawable.diji3);
		diji4 = BitmapFactory.decodeResource(getResources(), R.drawable.diji4);
		baozha1 = BitmapFactory.decodeResource(getResources(), R.drawable.baozha1);
		baozha2 = BitmapFactory.decodeResource(getResources(), R.drawable.baozha2);
		baozha4 = BitmapFactory.decodeResource(getResources(), R.drawable.baozha4);
		baozha5 = BitmapFactory.decodeResource(getResources(), R.drawable.baozha5);
		baozha6 = BitmapFactory.decodeResource(getResources(), R.drawable.baozha6);
		bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		zidan = BitmapFactory.decodeResource(getResources(), R.drawable.zidan);	
	
		erjihuancun = Bitmap.createBitmap(display_w, display_h, Config.ARGB_8888);//���������������Ƭ
		feijiImage = new FeijiImage();
		gameImages.add(new BeiJingImage(bg));
		gameImages.add(feijiImage);
		gameImages.add(new DirenImage());
	}
	
	private interface GameImage{
		public Bitmap getBitmap();
		public int getX();
		public int getY();
	}
	private class Zidan implements GameImage{//�ӵ�
		@SuppressWarnings("unused")
		private Bitmap zidan1;
		@SuppressWarnings("unused")
		private FeijiImage feiji;
		private int x;
		private int y;
		public Zidan(FeijiImage feiji,Bitmap zidan){
			this.feiji = feiji;
			this.zidan1 = zidan;
			
			x = (feiji.getX()+(feiji.getWidth()/2)-17);
			y =(feiji.getY()-zidan.getHeight()+80); 
		}
		public Bitmap getBitmap() {
			y-=45;
			if (y<=-100) {
				zidans.remove(this);
			}
			return zidan;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			
			return y;
		}
		
	}
	
	private class FeijiImage implements GameImage {//�Լ��ķɻ�
		private int x;
		private int y;
		private int width;
		private int height;
		private ArrayList<Bitmap> zijis = new ArrayList<Bitmap>();
		private ArrayList<Bitmap> bitmapbz = new ArrayList<Bitmap>();
		private FeijiImage(){
			zijis.add(my1);
			zijis.add(my2);
			zijis.add(my3);
			zijis.add(my4);
			bitmapbz.add(baozha1);//���˷ɻ���ը�������
			bitmapbz.add(baozha2);
			bitmapbz.add(baozha4);
			bitmapbz.add(baozha5);
			bitmapbz.add(baozha6);
			x = (display_w-my1.getWidth())/2;//�ɻ�������
			y = display_h-my1.getHeight()-30;
			width = my1.getWidth();//�Լ��ɻ��ĸߺͿ�
			height = my1.getHeight();	
		};
		
		private int index = 0;
		private int Num = 0;
		
		public Bitmap getBitmap() {
			Bitmap bitmap = zijis.get(index);
			if (Num == 9) {
				index++;
				if(index == 5 && state){
					gameImages.remove(this);//�Ƴ���ըͼ��
				}
				if (index == zijis.size()) {
					index = 0;
				}
				Num = 0;
			}
			Num++;
				
			return bitmap;
		}
		
		public void zijibaozha (ArrayList<DirenImage> direnImages){
			for (DirenImage direnImage : (ArrayList<DirenImage>)direnImages.clone()){
				if (
					  /*(((x+width)> direnImage.getX()) && ((y+height)>direnImage.getY())) || 
					  ((x<(direnImage.getX()+direnImage.getWidth())) && ((y+height)>direnImage.getY())) || 
					  ((x+width>direnImage.getX())&&(y<(direnImage.getY()+direnImage.getHeight()))) ||
					  ((y<(direnImage.getX()+direnImage.getWidth()))&&(y<(direnImage.getY()+direnImage.getHeight())))*/
						(x>direnImage.getX()-width+55)&&
						(x<direnImage.getX()+direnImage.getWidth()-55)&&
						(y>direnImage.getHeight()-height+66)&&
						(y<direnImage.getY()+direnImage.getHeight()-65)
				    )//ײ������
				{
				feijiImage.zijis = bitmapbz;
				
				feijiImage.getBitmap();
				Paint paint = new Paint();
				paint.setColor(Color.rgb(255, 255, 0));
				youxijieshu = 1;
				//stop();
				
				//gameImages.remove(feijiImage);
			}
			}
		}
		public int getWidth() {
			return width;	
		}
		public int getHeight(){
			return height;
		}

		public int getX() {
			
			return x;
		}

		public int getY() {
			
			return y;
		}
		
		public void setX(int x){//�϶�ʱ��ֵ
			this.x = x;
		}
		
		public void setY(int y){//�϶�ʱ��ֵ
			this.y = y;
		}
		
	}
	
	private class DirenImage implements GameImage {
		
		private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		private ArrayList<Bitmap> bitmapbz = new ArrayList<Bitmap>();
		private int x;
		private int y;
		private int width;
		private int height;
		public DirenImage() {
			bitmaps.add(diji1);//���˷ɻ��������
			bitmaps.add(diji2);
			bitmaps.add(diji3);
			bitmaps.add(diji4);
			bitmapbz.add(baozha1);//���˷ɻ���ը�������
			bitmapbz.add(baozha2);
			bitmapbz.add(baozha4);
			bitmapbz.add(baozha5);
			bitmapbz.add(baozha6);
			
			y = -diji1.getHeight();//�л����ֵ�λ��y
			Random random = new Random();
			x = random.nextInt(display_w-(diji1.getWidth()));//�л����ֵ�λ��x
			width = diji1.getWidth();//�л�ͼƬ��
			height = diji1.getHeight();//�л�ͼƬ��
		}
		
		private int index = 0;
		private int Num = 0;
		private int count = 0;
		private boolean state = false;
		Random random = new Random();
		
		public void shoudaogongji(ArrayList<Zidan> zidans){//�����յ�����ʱ�ķ����������ӵ���ʧ�л���ը
			if (!state) {
				
				for (GameImage zidan : (ArrayList<Zidan>)zidans.clone()){//�����ӵ���ÿ���ӵ��Ƿ���ел�������������ӵ���ʧ��
					if (zidan.getX()>x 
							&& zidan.getY()>y 
							&& zidan.getX()<width+x 
							&& zidan.getY()<height+y) {
						zidans.remove(zidan);//���ел����ӵ���ʧ
						count++;
						if (count == random.nextInt(4)+1) {//�����1.2.3.4
							//�����ڵ�����������������ͻ��ٵл���һ���࣬
							//��ô�ű�ը�����У���Щ�л�ʱ��Զ���ܱ�ը�ģ�
							//��Ϊrandom.nextInt(4)+1)���ܵ���
							state = true;
							bitmaps = bitmapbz;//����ָ�������ĵл����ᱻ�滻�ɱ�ը
							fengshu=fengshu+count*10;
							count = 0;
						}
						break;
					} 
					
				}
			}
		}
		public Bitmap getBitmap() {
			Bitmap bitmap = bitmaps.get(index);
			if (Num == 5) {//�л�ͼƬ�л��ٶ�
				index++;
				if(index == 5 && state){
					gameImages.remove(this);//�Ƴ���ըͼ��
					direnImages.remove(this);
				}
				if (index == bitmaps.size()) {
					index = 0;
				}
				Num = 0;
			}
			y+=direnfeijiyidong;
			Num++;
			if (y>display_h) {
				gameImages.remove(this);
			}
			return bitmap;
		}
		public int getWidth() {
			return width;	
		}
		
		public int getHeight(){
			return height;
		}
		
		public int getX() {
			
			return x;
		}

		
		public int getY() {
			return y;
		}
		
	}
	//���𱳾��Ĵ���
	private class BeiJingImage implements GameImage{
		private Bitmap bg;
		
		private BeiJingImage(Bitmap bg){
			this .bg = bg;
			newBitmap = Bitmap.createBitmap(display_w, display_h, Config.ARGB_8888);
		}
		private int height = 0;
		private Bitmap newBitmap = null;

		public Bitmap getBitmap() {
			
			Paint paint = new Paint();
			Canvas canvas = new Canvas(newBitmap);
			
			canvas.drawBitmap(bg, 
					new Rect(0, 0, bg.getWidth(), bg.getHeight()), 
					new Rect(0, height, display_w, display_h+height), 
					paint);
			canvas.drawBitmap(bg, 
					new Rect(0, 0, bg.getWidth(), bg.getHeight()), 
					new Rect(0, -display_h+height, display_w, height), 
					paint);
			height+=2;
			if (height >= display_h) {
				height = 0;
			}
			return newBitmap;	
		}
		public int getX() {
			
			return 0;
		}

		public int getY() {
			
			return 0;
		}
	} 
	private boolean state = false;
	private SurfaceHolder holder;
	private long fengshu = 0;
	private int guanqia = 1; 
	private int direnfeijiyidong = 6;
	private int chujishuliang = 25;
	private int zidanfashesudu = 8;
	private boolean stopstate = false;
	public void stop(){
		stopstate = true;
	}
	public void start(){
		stopstate = false;
		thread.interrupt();
	}
	//�滭����
	public void run() {
		Paint paint = new Paint();
		Paint paint2 = new Paint();
		Paint paint3 = new Paint();
		paint2.setColor(Color.rgb(200, 200, 200));
		paint2.setAntiAlias(true);
		paint2.setShadowLayer(20, 10, 10, Color.BLACK);
		paint2.setTypeface(Typeface.SANS_SERIF);
		paint2.setTextSize(70);
		paint3.setColor(Color.rgb(225, 225, 225));
		paint3.setAntiAlias(true);//�����
		paint3.setShadowLayer(20, 10, 10, Color.BLACK);
		paint3.setTypeface(Typeface.SANS_SERIF);
		paint3.setTextSize(140);
		int diren_number = 0;
		int zidan_number = 0;
		try {
			while (state){
				while (stopstate) {
					try{
						Thread.sleep(100000);
					
					}catch (Exception e) {
						
					}
				}
				if (selectfeiji!=null) {
					if (zidan_number==zidanfashesudu) {
						zidans.add(new Zidan(selectfeiji, zidan));//�����һ���ӵ��������ӵ������ӵ��б��з����ӵ����ٶ�
						zidan_number=0;
					}
					zidan_number++;
				}
				Canvas newCanvas = new Canvas(erjihuancun);
				for (GameImage image : (ArrayList<GameImage>)gameImages.clone()){//����gameImage�б�
					if (image instanceof DirenImage) {//���image��DirenImage��ʵ������ôimageת����DirenImage��
						//Ȼ��������ķ���
						((DirenImage)image).shoudaogongji(zidans);//���ӵ����ߵл�
						//((DirenImage)image).zijibaozha(feijiImage);//����DirenImage�е�,�ҷ��ɻ�����ը�ķ���
					}
					//feijiImage.zijibaozha(direnImages);
					
					
					newCanvas.drawBitmap(image.getBitmap(), image.getX(), image.getY(), paint);
				}
				/*for (GameImage image : (ArrayList<GameImage>) gameImages.clone()){
					if (image instanceof FeijiImage) {
						((FeijiImage)image).zijibaozha(direnImages);
					}
					newCanvas.drawBitmap(image.getBitmap(), image.getX(), image.getY(), paint);
				}*/
				
				
				for (Zidan image : (ArrayList<Zidan>) zidans.clone()){
					newCanvas.drawBitmap(image.getBitmap(), image.getX(), image.getY(), paint);
				}
				//����
				newCanvas.drawText("�� : "+fengshu, 50, 100, paint2);
				
				newCanvas.drawText("�� : "+guanqia+"/13", 50, 191, paint2);
				if (youxijieshu == 1) {
					newCanvas.drawText("��Ϸ������", (display_w-145*5)/2, (display_h-155)/2, paint3);
					stop();
				}
				

				
				if (fengshu>=100&&fengshu<200) {
					guanqia=2;
					direnfeijiyidong=7;
					chujishuliang=22;
					zidanfashesudu = 8;
				}else if (fengshu>=200&&fengshu<400) {
					guanqia=3;
					direnfeijiyidong=8;
					chujishuliang=19;
					zidanfashesudu = 8;
				}else if (fengshu>=400&&fengshu<700) {
					guanqia=4;
					direnfeijiyidong=9;
					chujishuliang=16;
					zidanfashesudu = 7;
				}else if (fengshu>=700&&fengshu<1100) {
					guanqia=5;
					direnfeijiyidong=11;
					chujishuliang=13;
					zidanfashesudu = 6;
				}else if (fengshu>=1100&&fengshu<1600) {
					guanqia=6;
					direnfeijiyidong=12;
					chujishuliang=12;
					zidanfashesudu = 5;
				}else if (fengshu>=1600&&fengshu<2200) {
					guanqia=7;
					direnfeijiyidong=13;
					chujishuliang=11;
					zidanfashesudu = 5;
				}else if(fengshu>=2200&&fengshu<2800){
					guanqia =8;
					direnfeijiyidong=14;
					chujishuliang=10;
					zidanfashesudu = 4;
				}
				else if(fengshu>=2800&&fengshu<3400){
					guanqia =9;
					direnfeijiyidong=15;
					chujishuliang=9;
					zidanfashesudu = 4;
				}
				else if(fengshu>=3400&&fengshu<4000){
					guanqia =10;
					direnfeijiyidong=16;
					chujishuliang=8;
					zidanfashesudu = 3;
				}
				else if(fengshu>=4000&&fengshu<4400){
					guanqia =11;
					direnfeijiyidong=17;
					chujishuliang=7;
					zidanfashesudu = 2;
				}
				else if(fengshu>=4400&&fengshu<4800){
					guanqia =12;
					direnfeijiyidong=18;
					chujishuliang=7;
					zidanfashesudu = 2;
				}else if(fengshu>=4800){
					guanqia =13;
					direnfeijiyidong=19;
					chujishuliang=6;
					zidanfashesudu = 1;
				}
				if (diren_number==chujishuliang) {
					DirenImage diRen = new DirenImage();
					gameImages.add(diRen);
					direnImages.add(diRen);
					diren_number = 0;
				}
				diren_number++;
				
				Canvas canvas = holder.lockCanvas();
				canvas.drawBitmap(erjihuancun, 0, 0, paint);
				holder.unlockCanvasAndPost(canvas);
				Thread.sleep(10);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
			
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		state = false;
	}
	
	public void surfaceChanged(SurfaceHolder holder, int fprmat, int width, int height) {
		display_w = width;
		display_h = height;
		init();
		this.holder = holder;
		state = true;
		thread = new Thread(this);
		thread.start();
	}
	Thread thread = null;
	FeijiImage selectfeiji;
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (GameImage game : gameImages){
				if (game instanceof FeijiImage) {
					FeijiImage feijiImage = (FeijiImage)game;
					if (feijiImage.getX()<event.getX()
							&& feijiImage.getY()<event.getY()
							&& feijiImage.getX() + feijiImage.getWidth() > event.getX()
							&& feijiImage.getY() + feijiImage.getHeight() > event.getY()) {
						selectfeiji = feijiImage;
					}
					else {
						selectfeiji = null;
					}
					break;
				}
			}
		}else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (selectfeiji!=null) {
				selectfeiji.setX((int)event.getX()-selectfeiji.getWidth()/2);
				selectfeiji.setY((int)event.getY()-selectfeiji.getHeight()/2);
			}
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
			selectfeiji = null;
		}
		return true;
	}

	
	
	

	
}
