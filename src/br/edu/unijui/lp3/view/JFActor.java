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

import br.edu.unijui.lp3.model.Actor;
import br.edu.unijui.lp3.server.ActorConnection;
import br.edu.unijui.lp3.view.utils.JOptionPaneUtils;
import br.edu.unijui.lp3.view.utils.JTableUtils;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings({"serial", "unchecked"})
public class JFActor extends JFrame {
	
	private JButton jbNovo;
	private JButton jbSalvarAtualizar;
	private JButton jbDeletar;
	private JLabel jlId;
	private JLabel jlLastUpdate;
	private JTabbedPane jtpCadastroPesquisa;
	private JTable jtActors;
	private JTextField jtfId;
	private JTextField jtfFirstName;
	private JTextField jtfLastName;
	private JTextField jtfLastUpdate;
	private ObjectTableModel<Actor> tmActors;
	
	private Actor actor;
	private ActorConnection connection = new ActorConnection();
	
	public JFActor() {
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		jlId = new JLabel("Id");
		jlId.setVisible(false);
		jtfId = new JTextField();
		jtfId.setEditable(false);
		jtfId.setVisible(false);
		jtfFirstName = new JTextField();
		jtfLastName = new JTextField();
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
		
		jtActors = JTableUtils.createTableActor();
		jtActors.addMouseListener(new TableActorsMouseAdapter());
		tmActors = (ObjectTableModel<Actor>) jtActors.getModel();
		
		jtpCadastroPesquisa = new JTabbedPane();
		jtpCadastroPesquisa.addTab("Cadastro", createPanelCadastro());
		jtpCadastroPesquisa.addTab("Pesquisa", createPanelPesquisa());
	}
	
	private JPanel createPanelCadastro() {
		JPanel jpCadastro = new JPanel(new MigLayout(new LC().hideMode(3), new AC().align("right")));
		jpCadastro.add(jlId, new CC().split(2));
		jpCadastro.add(jtfId, new CC().width("35!"));
		jpCadastro.add(new JLabel("Nome"));
		jpCadastro.add(jtfFirstName, new CC().width("100%").wrap());
		jpCadastro.add(new JLabel("Sobrenome"), new CC());
		jpCadastro.add(jtfLastName, new CC().width("100%").spanX().split());
		jpCadastro.add(jlLastUpdate);
		jpCadastro.add(jtfLastUpdate, new CC().width("95!"));
		jpCadastro.add(new JLabel(), new CC().newline().height("100%").wrap());
		jpCadastro.add(jbNovo, new CC().spanX().split().width("120!").height("45!").alignX("center"));
		jpCadastro.add(jbSalvarAtualizar, new CC().width("120!").height("45!"));
		jpCadastro.add(jbDeletar, new CC().width("120!").height("45!"));
		return jpCadastro;
	}
	
	private JPanel createPanelPesquisa() {
		JPanel jpPesquisa = new JPanel(new MigLayout());
		jpPesquisa.add(new JScrollPane(jtActors), new CC().width("100%").height("100%"));
		return jpPesquisa;
	}
	
	private void addComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new MigLayout());
		setTitle("Atores");
		
		add(jtpCadastroPesquisa, new CC().width("400:100%:").height("300:100%:"));
		
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		
		getData();
	}
	
	private void getData() {
		try {
			tmActors.setData(connection.list());
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar os dados do banco!");
			e.printStackTrace();
		}
	}
	
	private void clearFields() {
		actor = null;
		jlId.setVisible(false);
		jtfId.setText("");
		jtfId.setVisible(false);
		jtfFirstName.setText("");
		jtfLastName.setText("");
		jlLastUpdate.setVisible(false);
		jtfLastUpdate.setText("");
		jtfLastUpdate.setVisible(false);
		jbSalvarAtualizar.setText("Gravar");
		jbDeletar.setEnabled(false);
	}
	
	private void populateView() {
		jlId.setVisible(true);
		jtfId.setText(String.valueOf(actor.getActorId()));
		jtfId.setVisible(true);
		jtfFirstName.setText(actor.getFirstName());
		jtfLastName.setText(actor.getLastName());
		jlLastUpdate.setVisible(true);
		jtfLastUpdate.setText(actor.getLastUpdate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));
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
			String firstName = jtfFirstName.getText().trim();
			String lastName = jtfLastName.getText().trim();
			if (firstName.isEmpty()) {
				JOptionPaneUtils.showWarning("O campo 'Nome' é obrigatório!");
				return;
			} else if (lastName.isEmpty()) {
				JOptionPaneUtils.showWarning("O campo 'Sobrenome' é obrigatório!");
				return;
			} else if (JOptionPaneUtils.showQuestionMessage("Deseja " + jbSalvarAtualizar.getText().toLowerCase() + " o ator?")) {
				try {
					if (actor == null) {
						actor = new Actor();
						actor.setFirstName(firstName.toUpperCase());
						actor.setLastName(lastName.toUpperCase());
						connection.insert(actor);
					} else {
						actor.setFirstName(firstName.toUpperCase());
						actor.setLastName(lastName.toUpperCase());
						connection.update(actor);
					}
					JOptionPaneUtils.showMessage("Ator " + (jbSalvarAtualizar.getText().equals("Gravar") ?
							"gravado" : "atualizado") + " com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possivel " + jbSalvarAtualizar.getText().toLowerCase() +
							" o ator!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class ButtonDeletarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPaneUtils.showQuestionMessage("Deseja excluir o ator selecionado?")) {
				try {
					connection.delete(actor);
					JOptionPaneUtils.showMessage("Ator excluído com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possível excluir o ator!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class TableActorsMouseAdapter extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getClickCount() > 1) {
				actor = tmActors.getValue(jtActors.getSelectedRow());
				populateView();
			}
		}
	}
	
}