package hilogame;
/**游戏类，有三个主要界面，背景为图片
第一个：输入用户名，回车可以输入随机数种子，这里的文本框设置为只能接受数字，输入其他字符会自动清除，即无法输入其他字符
第二个：是否开始游戏，有肯定、否定与帮助选项
第三个：游戏界面，输入所猜数字，回车，会提醒是比答案大或小，界面显示所剩次数与所取范围。输入猜数字的文本框也被限定为只能输入100以下的数字
最后提供有保存和下一局即不保存按钮，保存会显示排行榜前三名（多了没有意义），以及历史按钮，即可以显示一个人的游戏历史的前三名（多了没有意义）
下面介绍程序的详细内容*/

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Image;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class HiLo extends javax.swing.JFrame {

	/**
	* Auto-generated main method to display this JFrame
	*/
    //这一部分所设置的变量会在下面对应部分一一注释说明
	private static final long serialVersionUID = 1L;
	private JLabel guessnumber;
	private JLabel boundary;
	private JTextField times;
	private JLabel timesleft;
	private JTextField guessreceive;
	private JLabel continuedescription;
	private JButton buttonyes,buttonno,help,save,next;
	private JLabel hilo;
	private JLabel userdescription,seedrequest;
	private JLabel labeluser,labelseed;
	private JLabel labelstart;
	private JTextField textuser,textseed;
	private JDialog alert;
	private String seednumber;
	private long randomnumber=0L;
	private int answer,guess,up=100,down=1,timesnumber=6;
	private Random generator;
	User one = new User();
	Database ranklist = new Database();

	public HiLo(){ //输入用户名，回车，输入随机数种子，最初的界面。随机数种子被设置为只能输入数字
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(HiLo.class.getResource("mark.jpg")).getImage()); //设置图标
		JPanel GImage = new JPanel() {                //插入背景图片
			  
			private static final long serialVersionUID = 7108736383800286336L;

			protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon(HiLo.class.getResource("back.jpg"));  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(),  
                        icon.getIconHeight(), icon.getImageObserver());  
            }  
  
        }; 
       setContentPane(GImage);
		
		getContentPane().setBackground(new java.awt.Color(232,232,232));
		hilo = new JLabel();                       //游戏标题
		getContentPane().add(hilo);
		hilo.setText("                          Now Hi-Lo game comes!!!");
		hilo.setPreferredSize(new java.awt.Dimension(412, 43));
		hilo.setFont(new java.awt.Font("Blackadder ITC",0,24));
		hilo.setBackground(new java.awt.Color(255,0,0));
		//创建用户名说明标签
		labeluser = new JLabel("Username：");     
		getContentPane().add(labeluser);
		labeluser.setPreferredSize(new java.awt.Dimension(104, 23));
		labeluser.setFont(new java.awt.Font("Andalus",0,18));
		labeluser.setText("Username:");
		labeluser.setBackground(new java.awt.Color(255,128,128));
		//创建用户名输入文本框
		textuser = new JTextField();
		getContentPane().add(textuser);
		textuser.setPreferredSize(new java.awt.Dimension(312, 24));
		textuser.setBackground(new java.awt.Color(255,255,255));
		textuser.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				String str=textuser.getText();
				if(str.length()>15){
					str=str.substring(0,str.length()-1);
		    		textuser.setText(str);
				}
				}
		});
		textuser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				textuserActionPerformed(evt);
			}
		});
		//用户名解释
		userdescription = new JLabel();
		getContentPane().add(userdescription);
		userdescription.setText("Please edit a username to record your grade.");
		userdescription.setPreferredSize(new java.awt.Dimension(404, 35));
		userdescription.setFont(new java.awt.Font("Courier New",2,14));
		//创建种子数说明标签
		labelseed = new JLabel("Seednumber:");
		getContentPane().add(labelseed);
		labelseed.setPreferredSize(new java.awt.Dimension(105, 31));
		labelseed.setFont(new java.awt.Font("Andalus",0,18));
		//创建种子数输入文本框
		textseed = new JTextField();
		getContentPane().add(textseed);
		textseed.setPreferredSize(new java.awt.Dimension(309, 24));
		textseed.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				String str=textseed.getText();
				char key=evt.getKeyChar();
			    if(((key<'0')||(key>'9'))&&str.length()>0){
			    	str=str.substring(0,str.length()-1);
		    		textseed.setText(str);
			    }
			    
				if(str.length()>=100000000000000000L){
					str=str.substring(0,str.length()-1);
		    		textseed.setText(str);
		    		}
				}
		});
		textseed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				textseedActionPerformed(evt);
			}
		});
		//种子数解释
		seedrequest = new JLabel("For initializing the random number generator.");
		getContentPane().add(seedrequest);
		seedrequest.setPreferredSize(new java.awt.Dimension(405, 34));
		seedrequest.setFont(new java.awt.Font("Courier New",2,14));
		seedrequest.setText("Used for initializing the random number generator.");
		pack();
		this.setSize(472, 231);
	}
	private void textuserActionPerformed(ActionEvent evt){ //用户名
		one.username = textuser.getText();
		if(one.username.equalsIgnoreCase("")) one.username = "anonymous";
		textseed.requestFocusInWindow();          //种子数文本框获得焦点
	}
	private void textseedActionPerformed(ActionEvent evt){ //随机数
			seednumber = textseed.getText();
			if(one.havefun==1){
				randomnumber = Long.parseLong(seednumber);
			    generator = new Random(randomnumber);           //用用户的种子数初始化种子数生成器
			}
			//把之前那个页面隐藏起来
			labeluser.setVisible(false);
			labelseed.setVisible(false);
			textuser.setVisible(false);
			textseed.setVisible(false);
			seedrequest.setVisible(false);
			userdescription.setVisible(false);
            //更改窗口大小与背景
			this.setSize(500,300);
			getContentPane().setBackground(new java.awt.Color(255,255,255));
			//询问是否愿意开始游戏
			labelstart = new JLabel();
			getContentPane().add(labelstart);
			labelstart.setText("Would you like to start a game? Please have a try~");
			labelstart.setFont(new java.awt.Font("Chiller",0,28));
			labelstart.setPreferredSize(new java.awt.Dimension(430, 80));
			//开始按钮，到游戏界面
			buttonyes = new JButton();
			getContentPane().add(buttonyes);
			buttonyes.setText("Yes. Let me challenge!");
			buttonyes.setBackground(new java.awt.Color(255,255,255));
			buttonyes.setPreferredSize(new java.awt.Dimension(251, 32));
			buttonyes.setFont(new java.awt.Font("Chiller",0,26));
			buttonyes.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					buttonyesActionPerformed(evt);
				}
			});
			//休息按钮，退出游戏
			buttonno = new JButton();
			getContentPane().add(buttonno);
			buttonno.setText("No.I want to have a break!");
			buttonno.setBackground(new java.awt.Color(255,255,255));
			buttonno.setPreferredSize(new java.awt.Dimension(251, 32));
			buttonno.setFont(new java.awt.Font("Chiller",0,26));
			buttonno.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					buttonnoActionPerformed(evt);
				}
			});
			//帮助按钮,弹出对话框显示游戏规则
			help = new JButton();
			getContentPane().add(help);
			help.setText("Help");
			help.setPreferredSize(new java.awt.Dimension(251, 32));
			help.setBackground(new java.awt.Color(255,255,255));
			help.setFont(new java.awt.Font("Chiller",0,26));
			help.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					helpActionPerformed(evt);
				}
			});
		}
	
	private void buttonyesActionPerformed(ActionEvent evt){//开始游戏
		//隐藏之前的界面
		hilo.setVisible(false);
		labelstart.setVisible(false);
		buttonyes.setVisible(false);
		buttonno.setVisible(false);
		help.setVisible(false);
		
		//游戏标题
		continuedescription = new JLabel();
		getContentPane().add(continuedescription);
		continuedescription.setText("There is a number between 1-100, guess!");
		continuedescription.setFont(new java.awt.Font("Chiller",0,36));
		continuedescription.setPreferredSize(new java.awt.Dimension(445, 111));
		//输入猜数字说明
		guessnumber = new JLabel();
		getContentPane().add(guessnumber);
		guessnumber.setText("Input the number you guess:");
		guessnumber.setFont(new java.awt.Font("Chiller",0,28));
		guessnumber.setPreferredSize(new java.awt.Dimension(263, 29));
		//输入猜数字文本框
		guessreceive = new JTextField();
		getContentPane().add(guessreceive);
		guessreceive.setPreferredSize(new java.awt.Dimension(84, 31));
		guessreceive.setFont(new java.awt.Font("Chiller",0,36));
		//剩余次数说明
		timesleft = new JLabel();
		getContentPane().add(timesleft);
		timesleft.setText("The times left you can guess:");
		timesleft.setFont(new java.awt.Font("Chiller",0,28));
		timesleft.setPreferredSize(new java.awt.Dimension(265, 33));
		//剩余次数显示
		times = new JTextField();
		getContentPane().add(times);
		times.setText("   "+timesnumber);
		times.setPreferredSize(new java.awt.Dimension(85, 33));
		times.setFont(new java.awt.Font("Chiller",0,36));
		//猜数字的范围
		boundary = new JLabel();
		getContentPane().add(boundary);
		boundary.setText("                   Boundary:     [1,100]");
		boundary.setPreferredSize(new java.awt.Dimension(358, 35));
		boundary.setFont(new java.awt.Font("Chiller",0,28));
	    if(randomnumber<0) answer = (int)Math.random()*101;
		else answer = generator.nextInt(101);
	    //设置用户只能输入100以下的数字
	    guessreceive.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				String str=guessreceive.getText();
				char key=evt.getKeyChar();
			    if(((key<'0')||(key>'9'))&&str.length()>0){
			    	str=str.substring(0,str.length()-1);
		    		guessreceive.setText(str);
			    }
			    
				str=guessreceive.getText();
				if(str.length()>=3){
					if(str.charAt(0)=='1'&&str.charAt(1)=='0'&&str.charAt(2)=='0'){}
					else{
						str=str.substring(0,str.length()-1);
			    		guessreceive.setText(str);
					}
				}
				}
		});
	    //用户回车后可提示高、低或回答正确
		guessreceive.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				guessreceiveActionPerformed(evt);
			}
		});		
	}
	
	public void guessreceiveActionPerformed(ActionEvent evt){ //判断用户所猜数字与答案关系并提示
		String a = guessreceive.getText();
		guess = Integer.parseInt(a);
		//如果用户输入数字超出边界则弹出对话框提醒，并不减少次数
		if(guess<down||guess>up){
			alert = new JDialog(this,true);
			alert.getContentPane().setLayout(new FlowLayout());
			alert.setTitle("Attention!!");
			alert.setFont(new java.awt.Font("Chiller",0,26));
			alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			alert.setBounds(600,350,320,80);
			alert.setResizable(false);
			JLabel attention = new JLabel();
			alert.getContentPane().add("Center",new JPanel().add(attention));
			attention.setText("This number is between "+down+" and "+up+".");
			attention.setFont(new java.awt.Font("Chiller",0,26));
			attention.setVisible(true);
			alert.setBackground(new java.awt.Color(255,255,255));
			alert.setVisible(true);
			guessreceive.setText(null);
		}
		//如果用户所猜数字大于答案，则提醒higher，并减少一次机会，设置合适范围
		else if(guess>answer){
			timesnumber--;
			//查看用户是否还有剩余机会继续猜测，如果没有则告诉用户答案，并重新询问是否进行下一次游戏
			if(timesnumber>0){
				alert = new JDialog(this,true);
				alert.getContentPane().setLayout(new FlowLayout());
				alert.setTitle("Attention!!");
				alert.setFont(new java.awt.Font("Chiller",0,26));
				alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				alert.setBounds(600,350,320,80);
				alert.setResizable(false);
				JLabel attention = new JLabel();
				alert.getContentPane().add("Center",new JPanel().add(attention));
				attention.setText("This number is higher than the answer.");
				attention.setFont(new java.awt.Font("Chiller",0,26));
				attention.setVisible(true);
				alert.setBackground(new java.awt.Color(255,255,255));
				alert.setVisible(true);
				guessreceive.setText(null);
				up = guess-1;
				times.setText("   "+timesnumber);
				boundary.setText("                   Boundary:    ["+down+','+up+']');
			}
			else
			{
				alert = new JDialog(this,true);
				alert.getContentPane().setLayout(new FlowLayout());
				alert.setTitle("Attention!!");
				alert.setFont(new java.awt.Font("Chiller",0,26));
				alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				alert.setBounds(600,350,320,150);
				alert.setResizable(false);
				JPanel p = new JPanel();
				JLabel attention = new JLabel();
				alert.getContentPane().add("Center",p.add(attention));
				attention.setText("This number is higher than the answer.");
				attention.setFont(new java.awt.Font("Chiller",0,26));
				attention.setVisible(true);
				JLabel lose = new JLabel();
				lose.setText("I'm sorry,you run out of the chance!");
				lose.setFont(new java.awt.Font("Chiller",0,26));
				alert.getContentPane().add("Center",p.add(lose));
				JLabel lose1 = new JLabel();
				lose1.setText("The answer is "+answer);
				lose1.setFont(new java.awt.Font("Chiller",0,26));
				alert.getContentPane().add("Center",p.add(lose1));
				alert.setBackground(new java.awt.Color(255,255,255));
				alert.setVisible(true);
				hilo.setVisible(true);
				labelstart.setVisible(true);
				buttonyes.setVisible(true);
				buttonno.setVisible(true);
				help.setVisible(true);
				continuedescription.setVisible(false);
				guessnumber.setVisible(false);
				guessreceive.setVisible(false);
				times.setVisible(false);
				timesleft.setVisible(false);
				boundary.setVisible(false);
				one.havefun++;
				up = 100;
				down = 1;
				timesnumber = 6;
			}
			
		}
		//如果用户所猜数字小于答案，则提醒lower，并减少一次机会，设置合适范围
		else if(guess<answer){
			timesnumber--;
			//查看用户是否还有剩余机会继续猜测，如果没有则告诉用户答案，并重新询问是否进行下一次游戏
			if(timesnumber>0){
				alert = new JDialog(this,true);
				alert.getContentPane().setLayout(new FlowLayout());
				alert.setTitle("Attention!!");
				alert.setFont(new java.awt.Font("Chiller",0,26));
				alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				alert.setBounds(600,350,320,80);
				alert.setResizable(false);
				JLabel attention = new JLabel();
				alert.getContentPane().add("Center",new JPanel().add(attention));
				attention.setText("This number is lower than the answer.");
				attention.setFont(new java.awt.Font("Chiller",0,26));
				attention.setVisible(true);
				alert.setBackground(new java.awt.Color(255,255,255));
				alert.setVisible(true);
				guessreceive.setText(null);
				down = guess+1;
				times.setText("   "+timesnumber);
				boundary.setText("                   Boundary:    ["+down+','+up+']');
			}
			else
			{
				alert = new JDialog(this,true);
				alert.getContentPane().setLayout(new FlowLayout());
				alert.setTitle("Attention!!");
				alert.setFont(new java.awt.Font("Chiller",0,26));
				alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				alert.setBounds(600,350,320,160);
				alert.setResizable(false);
				JPanel p = new JPanel();
				JLabel attention = new JLabel();
				alert.getContentPane().add("Center",p.add(attention));
				attention.setText("This number is lower than the answer.");
				attention.setFont(new java.awt.Font("Chiller",0,26));
				attention.setVisible(true);
				JLabel lose = new JLabel();
				lose.setText("I'm sorry,you run out of the chance!");
				lose.setFont(new java.awt.Font("Chiller",0,26));
				alert.getContentPane().add("Center",p.add(lose));
				JLabel lose1 = new JLabel();
				lose1.setText("The answer is "+answer);
				lose1.setFont(new java.awt.Font("Chiller",0,26));
				alert.getContentPane().add("Center",p.add(lose1));
				alert.setBackground(new java.awt.Color(255,255,255));
				alert.setVisible(true);
				hilo.setVisible(true);
				labelstart.setVisible(true);
				buttonyes.setVisible(true);
				buttonno.setVisible(true);
				continuedescription.setVisible(false);
				guessnumber.setVisible(false);
				guessreceive.setVisible(false);
				times.setVisible(false);
				timesleft.setVisible(false);
				boundary.setVisible(false);
				one.havefun++;
				up = 100;
				down = 1;
				timesnumber = 6;
			}
		}
		//用户猜中答案，恭喜他并询问是否愿意保存记录或开始下一局
		else{
			timesnumber--;
			alert = new JDialog(this,true);
			alert.getContentPane().setLayout(new FlowLayout());
			alert.setTitle("Attention!!");
			alert.setFont(new java.awt.Font("Chiller",0,26));
			alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			alert.setBounds(600,350,320,150);
			alert.setResizable(false);
			JLabel attention = new JLabel();
			JPanel p = new JPanel();
			alert.getContentPane().add("Center",p.add(attention));
			attention.setText("Congratulations! You are a smart guy!!!");
			attention.setFont(new java.awt.Font("Chiller",0,26));
			attention.setVisible(true);
			one.score=6-timesnumber;;
			JLabel succeed = new JLabel();
			succeed.setText("         You use "+one.score+" times.");
			succeed.setPreferredSize(new java.awt.Dimension(250, 32));
			succeed.setFont(new java.awt.Font("Chiller",0,26));
			alert.getContentPane().add("Center",p.add(succeed));
			
			save = new JButton();
			save.setText("Save");
			save.setFont(new java.awt.Font("Chiller",0,26));
			alert.getContentPane().add(p.add(save));
			save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
						try {
							saveActionPerformed(evt);
						} catch (SQLException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
				}
			});
			next = new JButton();
			next.setText("Next");
			next.setFont(new java.awt.Font("Chiller",0,26));
			alert.getContentPane().add(p.add(next));
			next.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					nextActionPerformed(evt);
				}
			});
			alert.setVisible(true);
			one.havefun++;
			up = 100;
			down = 1;
			timesnumber = 6;
			
		}
	}
	private void saveActionPerformed(ActionEvent evt) throws SQLException{ //保存并弹出排行榜
		//隐藏之前的界面并到询问是否开始游戏界面
		alert.setVisible(false);
		hilo.setVisible(true);
		labelstart.setVisible(true);
		buttonyes.setVisible(true);
		buttonno.setVisible(true);
		help.setVisible(true);
		continuedescription.setVisible(false);
		guessnumber.setVisible(false);
		guessreceive.setVisible(false);
		times.setVisible(false);
		timesleft.setVisible(false);
		boundary.setVisible(false);
		//向两张表里插数据
		ranklist.one = one;
		ranklist.insertAll();           
		ranklist.insertRank();
		
		ranklist.getRank();//得到排行榜,输出排行榜前三名
		ranklist.rs.afterLast();
		ranklist.rs.previous();
		//判断排行榜里是否有前三名，并按从高到低排列
		int totalrow = ranklist.rs.getRow();
		if(totalrow>=3){
			int[] row = new int[3];
			row[0]=row[1]=row[2]=0;
			for(int i=0;i<3;i++){
				ranklist.rs.beforeFirst();
				int best = 7;
				while(ranklist.rs.next()){
					if(best>ranklist.rs.getInt(2)){
						int j=0;
						for(j=0;j<i;j++){
							if(row[j]==ranklist.rs.getRow()) break;
						}
						if(i==j){
							best = ranklist.rs.getInt(2);
							row[i]=ranklist.rs.getRow();
						}
					}
				}
			}
			JDialog rank = new JDialog();
			rank.getContentPane().setLayout(new FlowLayout());
			rank.setTitle("                           Ranklist");
			rank.setFont(new java.awt.Font("Chiller",0,26));
			rank.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			rank.setBounds(550,300,250,210);
			rank.setResizable(false);
			JLabel heading = new JLabel();
			heading.setText("RANKLIST!!!");
			heading.setFont(new java.awt.Font("Chiller",0,26));
			rank.getContentPane().add("Center",new JPanel().add(heading));
			heading.setVisible(true);
			JLabel r0 = new JLabel();
			ranklist.rs.absolute(row[0]);
			r0.setText("1   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
			r0.setFont(new java.awt.Font("Chiller",0,26));
			r0.setVisible(true);
			JLabel r1 = new JLabel();
			rank.getContentPane().add("Center",new JPanel().add(r0));
			ranklist.rs.absolute(row[1]);
			r1.setText("2   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
			r1.setFont(new java.awt.Font("Chiller",0,26));
			r1.setVisible(true);
			JLabel r2 = new JLabel();
			rank.getContentPane().add("Center",new JPanel().add(r1));
			ranklist.rs.absolute(row[2]);
			r2.setText("3   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
			r2.setFont(new java.awt.Font("Chiller",0,26));
			rank.getContentPane().add("Center",new JPanel().add(r2));
			r2.setVisible(true);
			
			JButton history = new JButton();
			history.setText("History");
			history.setPreferredSize(new java.awt.Dimension(100, 30));
			history.setFont(new java.awt.Font("Chiller",0,26));
			history.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					try {
						historyActionPerformed(evt);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			rank.getContentPane().add("Center",new JPanel().add(history));

			rank.setVisible(true);
			
		}
		else{
			int[] row = new int[totalrow];
			for(int i=0;i<totalrow;i++){
				row[i]=0;
				int best = 7;
				ranklist.rs.beforeFirst();
				while(ranklist.rs.next()){
					if(best>ranklist.rs.getInt(2)){
						int j=0;
						for(j=0;j<i;j++){
							if(row[j]==ranklist.rs.getRow()) break;
						}
						if(i==j){
							best = ranklist.rs.getInt(2);
							row[i]=ranklist.rs.getRow();
						}
							
					}
				}
			}
			if(totalrow==1){
				JDialog rank = new JDialog();
				rank.getContentPane().setLayout(new FlowLayout());
				rank.setTitle("Ranklist");
				rank.setFont(new java.awt.Font("Chiller",0,26));
				rank.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				rank.setBounds(550,300,250,150);
				rank.setResizable(false);
				JLabel heading = new JLabel();
				heading.setText("RANKLIST!!!");
				heading.setFont(new java.awt.Font("Chiller",0,26));
				rank.getContentPane().add("Center",new JPanel().add(heading));
				heading.setVisible(true);
				JLabel r0 = new JLabel();
				rank.getContentPane().add("Center",new JPanel().add(r0));
				ranklist.rs.absolute(row[0]);
				r0.setText("1   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
				r0.setFont(new java.awt.Font("Chiller",0,26));
				r0.setVisible(true);
				
				JButton history = new JButton();
				history.setText("History");
				history.setPreferredSize(new java.awt.Dimension(100, 30));
				history.setFont(new java.awt.Font("Chiller",0,26));
				history.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						try {
							historyActionPerformed(evt);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				rank.getContentPane().add("Center",new JPanel().add(history));
	
				rank.setVisible(true);
				
			}
			else{
				JDialog rank = new JDialog();
				rank.getContentPane().setLayout(new FlowLayout());
				rank.setTitle("Ranklist");
				rank.setFont(new java.awt.Font("Chiller",0,26));
				rank.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				rank.setBounds(550,300,250,180);
				rank.setResizable(false);
				JLabel heading = new JLabel();
				heading.setText("RANKLIST!!!");
				heading.setFont(new java.awt.Font("Chiller",0,26));
				rank.getContentPane().add("Center",new JPanel().add(heading));
				heading.setVisible(true);
				JLabel r0 = new JLabel();
				rank.getContentPane().add("Center",new JPanel().add(r0));
				ranklist.rs.absolute(row[0]);
				r0.setText("1   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
				r0.setFont(new java.awt.Font("Chiller",0,26));
				r0.setVisible(true);
				JLabel r1 = new JLabel();
				rank.getContentPane().add("Center",new JPanel().add(r1));
				ranklist.rs.absolute(row[1]);
				r1.setText("2   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
				r1.setFont(new java.awt.Font("Chiller",0,26));
				r1.setVisible(true);
				
				JButton history = new JButton();
				history.setText("History");
				history.setPreferredSize(new java.awt.Dimension(100, 30));
				history.setFont(new java.awt.Font("Chiller",0,26));
				history.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						try {
							historyActionPerformed(evt);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
				rank.getContentPane().add("Center",new JPanel().add(history));
	
				rank.setVisible(true);
				
			}
		}
		
	}
	private void historyActionPerformed(ActionEvent evt) throws SQLException{ //显示一个人的历史记录
		ranklist.getHistory();
		ranklist.rs.afterLast();
		ranklist.rs.previous();
		int totalrow = ranklist.rs.getRow();
		//判断一个人的历史记录是否有三条，并按从高到低排列
		if(totalrow>=3){
			int[] row = new int[3];
			row[0]=row[1]=row[2]=0;
			for(int i=0;i<3;i++){
				ranklist.rs.beforeFirst();
				int best = 7;
				while(ranklist.rs.next()){
					if(best>ranklist.rs.getInt(2)){
						int j=0;
						for(j=0;j<i;j++){
							if(row[j]==ranklist.rs.getRow()) break;
						}
						if(i==j){
							best = ranklist.rs.getInt(2);
							row[i]=ranklist.rs.getRow();
						}
					}
				}
			}
			JDialog rank = new JDialog();
			rank.getContentPane().setLayout(new FlowLayout());
			rank.setTitle("History");
			rank.setFont(new java.awt.Font("Chiller",0,26));
			rank.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			rank.setBounds(550,300,250,210);
			rank.setResizable(false);
			JLabel heading = new JLabel();
			heading.setText("    HISTORY!!!");
			heading.setFont(new java.awt.Font("Chiller",0,26));
			rank.getContentPane().add("Center",new JPanel().add(heading));
			heading.setVisible(true);
			JLabel r0 = new JLabel();
			ranklist.rs.absolute(row[0]);
			r0.setText("1   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
			r0.setFont(new java.awt.Font("Chiller",0,26));
			r0.setVisible(true);
			JLabel r1 = new JLabel();
			rank.getContentPane().add("Center",new JPanel().add(r0));
			ranklist.rs.absolute(row[1]);
			r1.setText("2   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
			r1.setFont(new java.awt.Font("Chiller",0,26));
			r1.setVisible(true);
			JLabel r2 = new JLabel();
			rank.getContentPane().add("Center",new JPanel().add(r1));
			ranklist.rs.absolute(row[2]);
			r2.setText("3   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
			r2.setFont(new java.awt.Font("Chiller",0,26));
			rank.getContentPane().add("Center",new JPanel().add(r2));
			r2.setVisible(true);
			
			rank.setVisible(true);
			
		}
		else{
			int[] row = new int[totalrow];
			for(int i=0;i<totalrow;i++){
				row[i]=0;
				int best = 7;
				ranklist.rs.beforeFirst();
				while(ranklist.rs.next()){
					if(best>ranklist.rs.getInt(2)){
						int j=0;
						for(j=0;j<i;j++){
							if(row[j]==ranklist.rs.getRow()) break;
						}
						if(i==j){
							best = ranklist.rs.getInt(2);
							row[i]=ranklist.rs.getRow();
						}
							
					}
				}
			}
			if(totalrow==1){
				JDialog rank = new JDialog();
				rank.getContentPane().setLayout(new FlowLayout());
				rank.setTitle("History");
				rank.setFont(new java.awt.Font("Chiller",0,26));
				rank.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				rank.setBounds(550,300,250,150);
				rank.setResizable(false);
				JLabel heading = new JLabel();
				heading.setText("    HISTORY");
				heading.setFont(new java.awt.Font("Chiller",0,26));
				rank.getContentPane().add("Center",new JPanel().add(heading));
				heading.setVisible(true);
				JLabel r0 = new JLabel();
				rank.getContentPane().add("Center",new JPanel().add(r0));
				ranklist.rs.absolute(row[0]);
				r0.setText("1   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
				r0.setFont(new java.awt.Font("Chiller",0,26));
				r0.setVisible(true);
				
				
				rank.setVisible(true);
				
			}
			else{
				JDialog rank = new JDialog();
				rank.getContentPane().setLayout(new FlowLayout());
				rank.setTitle("History");
				rank.setFont(new java.awt.Font("Chiller",0,26));
				rank.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				rank.setBounds(550,300,250,180);
				rank.setResizable(false);
				JLabel heading = new JLabel();
				heading.setText("    HISTORY");
				heading.setFont(new java.awt.Font("Chiller",0,26));
				rank.getContentPane().add("Center",new JPanel().add(heading));
				heading.setVisible(true);
				JLabel r0 = new JLabel();
				rank.getContentPane().add("Center",new JPanel().add(r0));
				ranklist.rs.absolute(row[0]);
				r0.setText("1   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
				r0.setFont(new java.awt.Font("Chiller",0,26));
				r0.setVisible(true);
				JLabel r1 = new JLabel();
				rank.getContentPane().add("Center",new JPanel().add(r1));
				ranklist.rs.absolute(row[1]);
				r1.setText("2   "+ranklist.rs.getString(1)+" used "+ranklist.rs.getInt(2)+"  times");
				r1.setFont(new java.awt.Font("Chiller",0,26));
				r1.setVisible(true);
				
				rank.setVisible(true);
				
			}
		}
				
	}
	private void nextActionPerformed(ActionEvent evt){     //不保存进入询问是否开始游戏界面
		alert.setVisible(false);
		hilo.setVisible(true);
		labelstart.setVisible(true);
		buttonyes.setVisible(true);
		buttonno.setVisible(true);
		help.setVisible(true);
		continuedescription.setVisible(false);
		guessnumber.setVisible(false);
		guessreceive.setVisible(false);
		times.setVisible(false);
		timesleft.setVisible(false);
		boundary.setVisible(false);
	}
	private void buttonnoActionPerformed(ActionEvent evt){ //退出游戏
		ranklist.close();
		System.exit(0);
	}
	private void helpActionPerformed(ActionEvent evt){    ///游戏帮助
		JDialog helpContent = new JDialog();
		helpContent.getContentPane().setLayout(new FlowLayout());
		helpContent.setTitle("Help");
		helpContent.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		helpContent.setBounds(600,350,500,170);
		helpContent.setResizable(false);
		JTextArea text = new JTextArea(300,30);
		text.setText("    This name is called HiLo game. The system generates a random number between 1 and 100. You have six times to guess it. When you input your guess, we will tell you the number is higher or lower than the answer. Just have fun!");
		text.setFont(new java.awt.Font("Arial",0,20));
		text.setLineWrap(true);
		helpContent.add(new JPanel().add(text));
		helpContent.setVisible(true);
		
	}
}
