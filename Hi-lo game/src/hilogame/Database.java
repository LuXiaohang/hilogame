package hilogame;
//数据库
//数据库里有两张表，一张rank用来记录每个人的最好成绩，用来排名；一张all1用来记录所有成绩，用来找寻每个人的历史记录
import java.sql.*;
public class Database {
	public Connection connection;
	public Statement stmta,stmtr;
	public ResultSet rs;
	
	User one ; //要向数据库里放的用户信息
	
	public Database(){      //与数据库建立连接
		one=new User();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ranklist","root","abc");
			stmta = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmtr = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}
		catch(ClassNotFoundException ex){
			System.out.println("找不到数据库驱动程序");
		}
		catch(SQLException ex){
			System.out.println("不能建立与数据库的连接或不能建立语句对象");
		}
	}
	public void close(){   //关闭数据库等
		try{
			rs.close();
			stmta.close();
			stmtr.close();
			connection.close();
		}
		catch(Exception e){
			System.out.println("语句对象关闭失败");
		}
	}
	public void insertAll(){         //向数据库all1表里插入记录
		String s = "INSERT INTO All1(Username1,Score1) VALUES("+"'"+one.username+"'"+","+one.score+");";
		try{
			stmta.executeUpdate(s);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void insertRank(){       //向数据库rank里插入记录，先查重，再决定是否插入或更新或不做动作
		String s1 = "INSERT INTO Rank (Username,Score) VALUES('"+one.username+"','"+one.score+"');";
		getRank();
		try {
			rs.beforeFirst();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			int i = 0,j = 0;
			rs.beforeFirst();
			while(rs.next()){        //查重并比较大小
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void getHistory(){                  //取all1表里所有当前用户的记录
		String s = "SELECT * FROM All1 WHERE Username1 = '"+one.username+"';";
		try {
			rs=stmta.executeQuery(s);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void getRank(){                    //取rank表里所有人的最好记录
		String s = "SELECT * FROM Rank;";
		try {
			rs=stmtr.executeQuery(s);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
