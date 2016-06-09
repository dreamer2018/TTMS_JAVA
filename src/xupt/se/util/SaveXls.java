package xupt.se.util;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class SaveXls {
	
	private JTable table;

	public SaveXls(JTable table) {
		this.table = table;
	}

	public void saveXls() {

		JFileChooser chooser = new JFileChooser();
		

		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		

		int result = chooser.showSaveDialog(null); 
		if (result == JFileChooser.APPROVE_OPTION) {
			File fi = chooser.getSelectedFile();

			String file = fi.getAbsolutePath() + ".xls";
			System.out.println(file);
			try {

				FileWriter out = new FileWriter(file);
				

				for (int i = 0; i < table.getColumnCount(); i++) {
					out.write(table.getColumnName(i) + "\t");
				}
				out.write("\n");
				

				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						out.write(table.getValueAt(i, j).toString() + "\t");
					}
					out.write("\n");
				}
				

				out.close();
				JOptionPane.showMessageDialog(null, "浠跺烘");
				
				// 寮excel浠
				// Runtime.getRuntime().exec("cmd /c start \"\" \"" + file + "\"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			// 瑰诲娑浠
			// JOptionPane.showMessageDialog(null, "╀换浣璺");
		}
	}

}
