package hilogame;
/**��Ϸ�࣬��������Ҫ���棬����ΪͼƬ
��һ���������û������س�����������������ӣ�������ı�������Ϊֻ�ܽ������֣����������ַ����Զ���������޷����������ַ�
�ڶ������Ƿ�ʼ��Ϸ���п϶����������ѡ��
����������Ϸ���棬�����������֣��س����������Ǳȴ𰸴��С��������ʾ��ʣ��������ȡ��Χ����������ֵ��ı���Ҳ���޶�Ϊֻ������100���µ�����
����ṩ�б������һ�ּ������水ť���������ʾ���а�ǰ����������û�����壩���Լ���ʷ��ť����������ʾһ���˵���Ϸ��ʷ��ǰ����������û�����壩
������ܳ������ϸ����*/

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
    //��һ���������õı������������Ӧ����һһע��˵��
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

	public HiLo(){ //�����û������س���������������ӣ�����Ľ��档��������ӱ�����Ϊֻ����������
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(HiLo.class.getResource("mark.jpg")).getImage()); //����ͼ��
		JPanel GImage = new JPanel() {                //���뱳��ͼƬ
			  
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
		hilo = new JLabel();                       //��Ϸ����
		getContentPane().add(hilo);
		hilo.setText("                          Now Hi-Lo game comes!!!");
		hilo.setPreferredSize(new java.awt.Dimension(412, 43));
		hilo.setFont(new java.awt.Font("Blackadder ITC",0,24));
		hilo.setBackground(new java.awt.Color(255,0,0));
		//�����û���˵����ǩ
		labeluser = new JLabel("Username��");     
		getContentPane().add(labeluser);
		labeluser.setPreferredSize(new java.awt.Dimension(104, 23));
		labeluser.setFont(new java.awt.Font("Andalus",0,18));
		labeluser.setText("Username:");
		labeluser.setBackground(new java.awt.Color(255,128,128));
		//�����û��������ı���
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
		//�û�������
		userdescription = new JLabel();
		getContentPane().add(userdescription);
		userdescription.setText("Please edit a username to record your grade.");
		userdescription.setPreferredSize(new java.awt.Dimension(404, 35));
		userdescription.setFont(new java.awt.Font("Courier New",2,14));
		//����������˵����ǩ
		labelseed = new JLabel("Seednumber:");
		getContentPane().add(labelseed);
		labelseed.setPreferredSize(new java.awt.Dimension(105, 31));
		labelseed.setFont(new java.awt.Font("Andalus",0,18));
		//���������������ı���
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
		//����������
		seedrequest = new JLabel("For initializing the random number generator.");
		getContentPane().add(seedrequest);
		seedrequest.setPreferredSize(new java.awt.Dimension(405, 34));
		seedrequest.setFont(new java.awt.Font("Courier New",2,14));
		seedrequest.setText("Used for initializing the random number generator.");
		pack();
		this.setSize(472, 231);
	}
	private void textuserActionPerformed(ActionEvent evt){ //�û���
		one.username = textuser.getText();
		if(one.username.equalsIgnoreCase("")) one.username = "anonymous";
		textseed.requestFocusInWindow();          //�������ı����ý���
	}
	private void textseedActionPerformed(ActionEvent evt){ //�����
			seednumber = textseed.getText();
			if(one.havefun==1){
				randomnumber = Long.parseLong(seednumber);
			    generator = new Random(randomnumber);           //���û�����������ʼ��������������
			}
			//��֮ǰ�Ǹ�ҳ����������
			labeluser.setVisible(false);
			labelseed.setVisible(false);
			textuser.setVisible(false);
			textseed.setVisible(false);
			seedrequest.setVisible(false);
			userdescription.setVisible(false);
            //���Ĵ��ڴ�С�뱳��
			this.setSize(500,300);
			getContentPane().setBackground(new java.awt.Color(255,255,255));
			//ѯ���Ƿ�Ը�⿪ʼ��Ϸ
			labelstart = new JLabel();
			getContentPane().add(labelstart);
			labelstart.setText("Would you like to start a game? Please have a try~");
			labelstart.setFont(new java.awt.Font("Chiller",0,28));
			labelstart.setPreferredSize(new java.awt.Dimension(430, 80));
			//��ʼ��ť������Ϸ����
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
			//��Ϣ��ť���˳���Ϸ
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
			//������ť,�����Ի�����ʾ��Ϸ����
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
	
	private void buttonyesActionPerformed(ActionEvent evt){//��ʼ��Ϸ
		//����֮ǰ�Ľ���
		hilo.setVisible(false);
		labelstart.setVisible(false);
		buttonyes.setVisible(false);
		buttonno.setVisible(false);
		help.setVisible(false);
		
		//��Ϸ����
		continuedescription = new JLabel();
		getContentPane().add(continuedescription);
		continuedescription.setText("There is a number between 1-100, guess!");
		continuedescription.setFont(new java.awt.Font("Chiller",0,36));
		continuedescription.setPreferredSize(new java.awt.Dimension(445, 111));
		//���������˵��
		guessnumber = new JLabel();
		getContentPane().add(guessnumber);
		guessnumber.setText("Input the number you guess:");
		guessnumber.setFont(new java.awt.Font("Chiller",0,28));
		guessnumber.setPreferredSize(new java.awt.Dimension(263, 29));
		//����������ı���
		guessreceive = new JTextField();
		getContentPane().add(guessreceive);
		guessreceive.setPreferredSize(new java.awt.Dimension(84, 31));
		guessreceive.setFont(new java.awt.Font("Chiller",0,36));
		//ʣ�����˵��
		timesleft = new JLabel();
		getContentPane().add(timesleft);
		timesleft.setText("The times left you can guess:");
		timesleft.setFont(new java.awt.Font("Chiller",0,28));
		timesleft.setPreferredSize(new java.awt.Dimension(265, 33));
		//ʣ�������ʾ
		times = new JTextField();
		getContentPane().add(times);
		times.setText("   "+timesnumber);
		times.setPreferredSize(new java.awt.Dimension(85, 33));
		times.setFont(new java.awt.Font("Chiller",0,36));
		//�����ֵķ�Χ
		boundary = new JLabel();
		getContentPane().add(boundary);
		boundary.setText("                   Boundary:     [1,100]");
		boundary.setPreferredSize(new java.awt.Dimension(358, 35));
		boundary.setFont(new java.awt.Font("Chiller",0,28));
	    if(randomnumber<0) answer = (int)Math.random()*101;
		else answer = generator.nextInt(101);
	    //�����û�ֻ������100���µ�����
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
	    //�û��س������ʾ�ߡ��ͻ�ش���ȷ
		guessreceive.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				guessreceiveActionPerformed(evt);
			}
		});		
	}
	
	public void guessreceiveActionPerformed(ActionEvent evt){ //�ж��û�����������𰸹�ϵ����ʾ
		String a = guessreceive.getText();
		guess = Integer.parseInt(a);
		//����û��������ֳ����߽��򵯳��Ի������ѣ��������ٴ���
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
		//����û��������ִ��ڴ𰸣�������higher��������һ�λ��ᣬ���ú��ʷ�Χ
		else if(guess>answer){
			timesnumber--;
			//�鿴�û��Ƿ���ʣ���������²⣬���û��������û��𰸣�������ѯ���Ƿ������һ����Ϸ
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
		//����û���������С�ڴ𰸣�������lower��������һ�λ��ᣬ���ú��ʷ�Χ
		else if(guess<answer){
			timesnumber--;
			//�鿴�û��Ƿ���ʣ���������²⣬���û��������û��𰸣�������ѯ���Ƿ������һ����Ϸ
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
		//�û����д𰸣���ϲ����ѯ���Ƿ�Ը�Ᵽ���¼��ʼ��һ��
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
							// TODO �Զ����ɵ� catch ��
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
	private void saveActionPerformed(ActionEvent evt) throws SQLException{ //���沢�������а�
		//����֮ǰ�Ľ��沢��ѯ���Ƿ�ʼ��Ϸ����
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
		//�����ű��������
		ranklist.one = one;
		ranklist.insertAll();           
		ranklist.insertRank();
		
		ranklist.getRank();//�õ����а�,������а�ǰ����
		ranklist.rs.afterLast();
		ranklist.rs.previous();
		//�ж����а����Ƿ���ǰ�����������Ӹߵ�������
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
	private void historyActionPerformed(ActionEvent evt) throws SQLException{ //��ʾһ���˵���ʷ��¼
		ranklist.getHistory();
		ranklist.rs.afterLast();
		ranklist.rs.previous();
		int totalrow = ranklist.rs.getRow();
		//�ж�һ���˵���ʷ��¼�Ƿ��������������Ӹߵ�������
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
	private void nextActionPerformed(ActionEvent evt){     //���������ѯ���Ƿ�ʼ��Ϸ����
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
	private void buttonnoActionPerformed(ActionEvent evt){ //�˳���Ϸ
		ranklist.close();
		System.exit(0);
	}
	private void helpActionPerformed(ActionEvent evt){    ///��Ϸ����
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
