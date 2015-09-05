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
import javax.swing.ListSelectionModel;

import com.towel.el.FieldResolver;
import com.towel.el.factory.FieldResolverFactory;
import com.towel.swing.table.ObjectTableModel;

import br.edu.unijui.lp3.model.Language;
import br.edu.unijui.lp3.server.LanguageConnection;
import br.edu.unijui.lp3.view.utils.JOptionPaneUtils;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class JFLanguage extends JFrame {
	
	private JButton jbNovo;
	private JButton jbSalvarAtualizar;
	private JButton jbDeletar;
	private JLabel jlId;
	private JLabel jlLastUpdate;
	private JTabbedPane jtpCadastroPesquisa;
	private JTable jtLanguages;
	private JTextField jtfId;
	private JTextField jtfName;
	private JTextField jtfLastUpdate;
	private ObjectTableModel<Language> tmLanguages;
	
	private Language language;
	private LanguageConnection connection = new LanguageConnection();
	
	public JFLanguage() {
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
		
		FieldResolverFactory frFac = new FieldResolverFactory(Language.class);
		FieldResolver frId = frFac.createResolver("languageId", "ID");
		FieldResolver frName = frFac.createResolver("name", "Name");
		tmLanguages = new ObjectTableModel<Language>(new FieldResolver[] {frId, frName});
		jtLanguages = new JTable(tmLanguages);
		jtLanguages.getTableHeader().setReorderingAllowed(false);
		jtLanguages.getColumnModel().getColumn(0).setMinWidth(50);
		jtLanguages.getColumnModel().getColumn(0).setMaxWidth(50);
		jtLanguages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtLanguages.addMouseListener(new TableLanguagesMouseAdapter());
		
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
		jpCadastro.add(jtfLastUpdate, new CC().width("100!").spanX().wrap());
		jpCadastro.add(new JLabel(), new CC().height("100%").wrap());
		jpCadastro.add(jbNovo, new CC().spanX().split().width("120!").height("45!").alignX("center"));
		jpCadastro.add(jbSalvarAtualizar, new CC().width("120!").height("45!"));
		jpCadastro.add(jbDeletar, new CC().width("120!").height("45!"));
		return jpCadastro;
	}
	
	private JPanel createPanelPesquisa() {
		JPanel jpPesquisa = new JPanel(new MigLayout());
		jpPesquisa.add(new JScrollPane(jtLanguages), new CC().width("100%").height("100%"));
		return jpPesquisa;
	}
	
	private void addComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new MigLayout(new LC().hideMode(3)));
		setTitle("Languages");
		
		add(jtpCadastroPesquisa, new CC().width("400:100%:").height("300:100%:"));
		
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		
		getData();
	}
	
	private void getData() {
		try {
			tmLanguages.setData(connection.list());
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar os dados do banco!");
			e.printStackTrace();
		}
	}
	
	private void clearFields() {
		language = null;
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
		jtfId.setText(String.valueOf(language.getLanguageId()));
		jtfId.setVisible(true);
		jtfName.setText(language.getName());
		jlLastUpdate.setVisible(true);
		jtfLastUpdate.setText(language.getLastUpdate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));
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
			} else if (JOptionPaneUtils.showQuestionMessage("Deseja " + jbSalvarAtualizar.getText().toLowerCase() + " o registro?")) {
				try {
					if (language == null) {
						language = new Language();
						language.setName(name);
						connection.insert(language);
					} else {
						language.setName(name);
						connection.update(language);
					}
					JOptionPaneUtils.showMessage("Registro " + (jbSalvarAtualizar.getText().equals("Gravar") ? "gravado" : "atualizado")
							+ " com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possivel " + jbSalvarAtualizar.getText().toLowerCase() + " o registro!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class ButtonDeletarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPaneUtils.showQuestionMessage("Deseja excluir o registro selecionado?")) {
				try {
					connection.delete(language);
					JOptionPaneUtils.showMessage("O registro foi excluído com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possível excluir o registro!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class TableLanguagesMouseAdapter extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getClickCount() > 1) {
				language = tmLanguages.getValue(jtLanguages.getSelectedRow());
				populateView();
			}
		}
	}
	
}