package hilogame;
//���ݿ�
//���ݿ��������ű�һ��rank������¼ÿ���˵���óɼ�������������һ��all1������¼���гɼ���������Ѱÿ���˵���ʷ��¼
import java.sql.*;
public class Database {
	public Connection connection;
	public Statement stmta,stmtr;
	public ResultSet rs;
	
	User one ; //Ҫ�����ݿ���ŵ��û���Ϣ
	
	public Database(){      //�����ݿ⽨������
		one=new User();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ranklist","root","abc");
			stmta = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmtr = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(ClassNotFoundException ex){
			System.out.println("�Ҳ������ݿ���������");
		}
		catch(SQLException ex){
			System.out.println("���ܽ��������ݿ�����ӻ��ܽ���������");
		}
	}
	public void close(){   //�ر����ݿ��
		try{
			rs.close();
			stmta.close();
			stmtr.close();
			connection.close();
		}
		catch(Exception e){
			System.out.println("������ر�ʧ��");
		}
	}
	public void insertAll(){         //�����ݿ�all1��������¼
		String s = "INSERT INTO All1(Username1,Score1) VALUES("+"'"+one.username+"'"+","+one.score+");";
		try{
			stmta.executeUpdate(s);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void insertRank(){       //�����ݿ�rank������¼���Ȳ��أ��پ����Ƿ�������»�������
		String s1 = "INSERT INTO Rank (Username,Score) VALUES('"+one.username+"','"+one.score+"');";
		getRank();
		try {
			rs.beforeFirst();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		try {
			int i = 0,j = 0;
			rs.beforeFirst();
			while(rs.next()){        //���ز��Ƚϴ�С
				if(one.username.equals(rs.getString(1))){
					if(one.score<rs.getInt(2)){
						String s2 = "DELETE FROM Rank WHERE Username = '"+one.username+"';";
						stmtr.executeUpdate(s2);
						i++;
						break;
					}
					j++;
				}
			}
			if(i==1||j==0) stmtr.executeUpdate(s1);
			
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void getHistory(){                  //ȡall1�������е�ǰ�û��ļ�¼
		String s = "SELECT * FROM All1 WHERE Username1 = '"+one.username+"';";
		try {
			rs=stmta.executeQuery(s);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void getRank(){                    //ȡrank���������˵���ü�¼
		String s = "SELECT * FROM Rank;";
		try {
			rs=stmtr.executeQuery(s);
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
