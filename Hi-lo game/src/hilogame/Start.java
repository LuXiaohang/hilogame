package hilogame;

import java.sql.SQLException;

//��ʼ��Ϸ

public class Start {
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args){
		
		//TODO �Զ����ɵķ������
		HiLo mygame = new HiLo();
		mygame.setLocationRelativeTo(null);
		mygame.setVisible(true);

	}

}
