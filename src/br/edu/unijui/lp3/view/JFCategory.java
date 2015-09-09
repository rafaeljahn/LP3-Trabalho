package br.edu.unijui.lp3.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.towel.swing.table.ObjectTableModel;

import br.edu.unijui.lp3.model.Category;
import br.edu.unijui.lp3.server.CategoryConnection;
import br.edu.unijui.lp3.view.utils.JOptionPaneUtils;
import br.edu.unijui.lp3.view.utils.JTableUtils;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings({"serial", "unchecked"})
public class JFCategory extends JFrame {
	
	private JButton jbNovo;
	private JButton jbSalvarAtualizar;
	private JButton jbDeletar;
	private JLabel jlId;
	private JLabel jlLastUpdate;
	private JTabbedPane jtpCadastroPesquisa;
	private JTable jtCategories;
	private JTextField jtfId;
	private JTextField jtfName;
	private JTextField jtfLastUpdate;
	private ObjectTableModel<Category> tmCategories;
	
	private Category category;
	private CategoryConnection connection = new CategoryConnection();
	
	public JFCategory() {
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		jlId = new JLabel("Id");
		jlId.setVisible(false);
		jtfId = new JTextField();
		jtfId.setEditable(false);
		jtfId.setVisible(false);
		jtfName = new JTextField();
		jlLastUpdate = new JLabel("Ultima atualização");
		jlLastUpdate.setVisible(false);
		jtfLastUpdate = new JTextField();
		jtfLastUpdate.setEditable(false);
		jtfLastUpdate.setVisible(false);
		jbNovo = new JButton("Novo", new ImageIcon(getClass().getResource("img").getPath().concat("/new32x32.png")));
		jbNovo.addActionListener(new ButtonNovoActionListener());
		jbSalvarAtualizar = new JButton("Gravar", new ImageIcon(getClass().getResource("img").getPath().concat("/save32x32.png")));
		jbSalvarAtualizar.addActionListener(new ButtonSalvarAtualizarActionListener());
		jbDeletar = new JButton("Deletar", new ImageIcon(getClass().getResource("img").getPath().concat("/delete32x32.png")));
		jbDeletar.addActionListener(new ButtonDeletarActionListener());
		jbDeletar.setEnabled(false);
		
		jtCategories = JTableUtils.createTableCategory();
		jtCategories.addMouseListener(new TableCategoriesMouseAdapter());
		tmCategories = (ObjectTableModel<Category>) jtCategories.getModel();
		
		jtpCadastroPesquisa = new JTabbedPane();
		jtpCadastroPesquisa.addTab("Cadastro", createPanelCadastro());
		jtpCadastroPesquisa.addTab("Pesquisa", createPanelPesquisa());
	}
	
	private JPanel createPanelCadastro() {
		JPanel jpCadastro = new JPanel(new MigLayout(new LC().hideMode(3)));
		jpCadastro.add(jlId);
		jpCadastro.add(jtfId, new CC().width("35!"));
		jpCadastro.add(new JLabel("Nome"));
		jpCadastro.add(jtfName, new CC().width("100%").wrap());
		jpCadastro.add(jlLastUpdate, new CC().spanX(3));
		jpCadastro.add(jtfLastUpdate, new CC().width("95!").spanX().wrap());
		jpCadastro.add(new JLabel(), new CC().height("100%").wrap());
		jpCadastro.add(jbNovo, new CC().spanX().split().width("120!").height("45!").alignX("center"));
		jpCadastro.add(jbSalvarAtualizar, new CC().width("120!").height("45!"));
		jpCadastro.add(jbDeletar, new CC().width("120!").height("45!"));
		return jpCadastro;
	}
	
	private JPanel createPanelPesquisa() {
		JPanel jpPesquisa = new JPanel(new MigLayout());
		jpPesquisa.add(new JScrollPane(jtCategories), new CC().width("100%").height("100%"));
		return jpPesquisa;
	}
	
	private void addComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new MigLayout(new LC().hideMode(3)));
		setTitle("Categorias");
		
		add(jtpCadastroPesquisa, new CC().width("400:100%:").height("300:100%:"));
		
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		
		getData();
	}
	
	private void getData() {
		try {
			tmCategories.setData(connection.list());
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar os dados do banco!");
			e.printStackTrace();
		}
	}
	
	private void clearFields() {
		category = null;
		jlId.setVisible(false);
		jtfId.setText("");
		jtfId.setVisible(false);
		jtfName.setText("");
		jlLastUpdate.setVisible(false);
		jtfLastUpdate.setText("");
		jtfLastUpdate.setVisible(false);
		jbSalvarAtualizar.setText("Gravar");
		jbDeletar.setEnabled(false);
	}
	
	private void populateView() {
		jlId.setVisible(true);
		jtfId.setText(String.valueOf(category.getCategoryId()));
		jtfId.setVisible(true);
		jtfName.setText(category.getName());
		jlLastUpdate.setVisible(true);
		jtfLastUpdate.setText(category.getLastUpdate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));
		jtfLastUpdate.setVisible(true);
		jbSalvarAtualizar.setText("Atualizar");
		jbDeletar.setEnabled(true);
		jtpCadastroPesquisa.setSelectedIndex(0);
	}
	
	private class ButtonNovoActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			clearFields();
		}
	}
	
	private class ButtonSalvarAtualizarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = jtfName.getText().trim();
			if (name.isEmpty()) {
				JOptionPaneUtils.showWarning("O campo 'Nome' é obrigatório!");
				return;
			} else if (JOptionPaneUtils.showQuestionMessage("Deseja " + jbSalvarAtualizar.getText().toLowerCase() + " a categoria?")) {
				try {
					if (category == null) {
						category = new Category();
						category.setName(name);
						connection.insert(category);
					} else {
						category.setName(name);
						connection.update(category);
					}
					JOptionPaneUtils.showMessage("Categoria " + (jbSalvarAtualizar.getText().equals("Gravar") ?
							"gravada" : "atualizada") + " com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possivel " + jbSalvarAtualizar.getText().toLowerCase() +
							" a categoria!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class ButtonDeletarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPaneUtils.showQuestionMessage("Deseja excluir a categoria selecionado?")) {
				try {
					connection.delete(category);
					JOptionPaneUtils.showMessage("Categoria excluído com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possível excluir a categoria!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class TableCategoriesMouseAdapter extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getClickCount() > 1) {
				category = tmCategories.getValue(jtCategories.getSelectedRow());
				populateView();
			}
		}
	}
	
}