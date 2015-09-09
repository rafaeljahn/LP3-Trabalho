package br.edu.unijui.lp3.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.towel.swing.table.ObjectTableModel;

import br.edu.unijui.lp3.model.Actor;
import br.edu.unijui.lp3.model.Category;
import br.edu.unijui.lp3.model.Film;
import br.edu.unijui.lp3.model.Language;
import br.edu.unijui.lp3.server.ActorConnection;
import br.edu.unijui.lp3.server.CategoryConnection;
import br.edu.unijui.lp3.server.FilmConnection;
import br.edu.unijui.lp3.server.LanguageConnection;
import br.edu.unijui.lp3.view.utils.JOptionPaneUtils;
import br.edu.unijui.lp3.view.utils.JTableUtils;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import br.edu.unijui.lp3.model.Film.Rating;

@SuppressWarnings({"serial", "unchecked"})
public class JFFilm extends JFrame {
	
	private JButton jbNovo;
	private JButton jbSalvarAtualizar;
	private JButton jbDeletar;
	private JButton jbAddActor;
	private JButton jbAddCategory;
	private JButton jbRemoveActor;
	private JButton jbRemoveCategory;
	private JButton jbLanguages;
	private JButton jbActors;
	private JButton jbCategories;
	private JComboBox<Language> jcbLanguage;
	private JComboBox<Language> jcbOriginalLanguage;
	private JComboBox<Rating> jcbRating;
	private JComboBox<Actor> jcbActor;
	private JComboBox<Category> jcbCategories;
	private JFormattedTextField jftfRentalRate;
	private JFormattedTextField jftfReplaceCost;
	private JLabel jlId;
	private JLabel jlLastUpdate;
	private JSpinner jsYear;
	private JSpinner jsRentalDuration;
	private JSpinner jsDuration;
	private JTabbedPane jtpCadastroPesquisa;
	private JTabbedPane jtpFilmActorCategory;
	private JTable jtFilms;
	private JTable jtActors;
	private JTable jtCategories;
	private JTextArea jtaDescription;
	private JTextField jtfId;
	private JTextField jtfTitle;
	private JTextField jtfSpecialFeatures;
	private JTextField jtfLastUpdate;
	private ObjectTableModel<Film> tmFilms;
	private ObjectTableModel<Actor> tmActors;
	private ObjectTableModel<Category> tmCategories;
	
	private Film film;
	private FilmConnection filmConnection = new FilmConnection();
	private LanguageConnection languageConnection = new LanguageConnection();
	private ActorConnection actorConnection = new ActorConnection();
	private CategoryConnection categoryConnection = new CategoryConnection();
	
	public JFFilm() {
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		jlId = new JLabel("Id");
		jlId.setVisible(false);
		jtfId = new JTextField();
		jtfId.setEditable(false);
		jtfId.setVisible(false);
		jtfTitle = new JTextField();
		jtaDescription = new JTextArea();
		jtaDescription.setLineWrap(true);
		jtaDescription.setWrapStyleWord(true);
		jsYear = new JSpinner(new SpinnerNumberModel(2015, 1900, 2100, 1));
		jcbLanguage = new JComboBox<Language>();
		jcbOriginalLanguage = new JComboBox<Language>();
		jbLanguages = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/language16x16.png")));
		jbLanguages.setToolTipText("Cadastro de linguagens");
		jbLanguages.addActionListener(new ButtonLanguagesActionListener());
		jsRentalDuration = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
		jftfRentalRate = new JFormattedTextField(NumberFormat.getNumberInstance());
		jftfRentalRate.setValue(0);
		jsDuration = new JSpinner(new SpinnerNumberModel(0, 0, 300, 1));
		jftfReplaceCost = new JFormattedTextField(NumberFormat.getNumberInstance());
		jftfReplaceCost.setValue(0);
		jcbRating = new JComboBox<Rating>(Rating.values());
		jtfSpecialFeatures = new JTextField();
		jlLastUpdate = new JLabel("Ultima atualização");
		jlLastUpdate.setVisible(false);
		jtfLastUpdate = new JTextField();
		jtfLastUpdate.setEditable(false);
		jtfLastUpdate.setVisible(false);
		
		jcbActor = new JComboBox<Actor>();
		jbAddActor = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/add16x16.png")));
		jbAddActor.setToolTipText("Adiciona um ator ao filme selecionado");
		jbAddActor.addActionListener(new ButtonAddActorActionListener());
		jbRemoveActor = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/remove16x16.png")));
		jbRemoveActor.setToolTipText("Remove um ator do filme selecionado");
		jbRemoveActor.addActionListener(new ButtonRemoveActorActionListener());
		jbActors = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/actor16x16.png")));
		jbActors.setToolTipText("Cadastro de atores");
		jbActors.addActionListener(new ButtonActorsActionListener());
		jtActors = JTableUtils.createTableActor();
		tmActors = (ObjectTableModel<Actor>) jtActors.getModel();
		
		jcbCategories = new JComboBox<Category>();
		jbAddCategory = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/add16x16.png")));
		jbAddCategory.setToolTipText("Adiciona uma categoria ao filme selecionado");
		jbAddCategory.addActionListener(new ButtonAddCategoryActionListener());
		jbRemoveCategory = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/remove16x16.png")));
		jbRemoveCategory.setToolTipText("Remove uma categoria do filme selecionado");
		jbRemoveCategory.addActionListener(new ButtonRemoveCategoryActionListener());
		jbCategories = new JButton(new ImageIcon(getClass().getResource("img").getPath().concat("/categories16x16.png")));
		jbCategories.setToolTipText("Cadastro de categorias");
		jbCategories.addActionListener(new ButtonCategoriesActionListener());
		jtCategories = JTableUtils.createTableCategory();
		tmCategories = (ObjectTableModel<Category>) jtCategories.getModel();
		
		jtpFilmActorCategory = new JTabbedPane();
		jtpFilmActorCategory.addTab("Filme", createPanelFilme());
		jtpFilmActorCategory.addTab("Atores", createPanelAtores());
		jtpFilmActorCategory.addTab("Categorias", createPanelCategorias());
		
		jbNovo = new JButton("Novo", new ImageIcon(getClass().getResource("img").getPath().concat("/new32x32.png")));
		jbNovo.addActionListener(new ButtonNovoActionListener());
		jbSalvarAtualizar = new JButton("Gravar", new ImageIcon(getClass().getResource("img").getPath().concat("/save32x32.png")));
		jbSalvarAtualizar.addActionListener(new ButtonSalvarAtualizarActionListener());
		jbDeletar = new JButton("Deletar", new ImageIcon(getClass().getResource("img").getPath().concat("/delete32x32.png")));
		jbDeletar.addActionListener(new ButtonDeletarActionListener());
		jbDeletar.setEnabled(false);
		
		jtFilms = JTableUtils.createTableFilms();
		jtFilms.addMouseListener(new TableFilmsMouseAdapter());
		tmFilms = (ObjectTableModel<Film>) jtFilms.getModel();
		
		jtpCadastroPesquisa = new JTabbedPane();
		jtpCadastroPesquisa.addTab("Cadastro", createPanelCadastro());
		jtpCadastroPesquisa.addTab("Pesquisa", createPanelPesquisa());
	}
	
	private JPanel createPanelFilme() {
		JScrollPane jspDescricao = new JScrollPane(jtaDescription);
		jspDescricao.setBorder(BorderFactory.createTitledBorder("Descrição"));
		
		JPanel jpFilme = new JPanel(new MigLayout(new LC().hideMode(3).noGrid()));
		jpFilme.add(jlId);
		jpFilme.add(jtfId, new CC().width("35!"));
		jpFilme.add(new JLabel("Titulo"));
		jpFilme.add(jtfTitle, new CC().width("100%").spanX().wrap());
		jpFilme.add(jspDescricao, new CC().width("100%").height("80!").spanX().wrap());
		jpFilme.add(new JLabel("Duração"));
		jpFilme.add(jsDuration, new CC().width("50:25%:"));
		jpFilme.add(new JLabel("Ano"));
		jpFilme.add(jsYear, new CC().width("60:25%:"));
		jpFilme.add(new JLabel("Dur. Aluguel"));
		jpFilme.add(jsRentalDuration, new CC().width("40:25%:"));
		jpFilme.add(new JLabel("Taxa Aluguel ($)"));
		jpFilme.add(jftfRentalRate, new CC().width("50:25%:").wrap());
		jpFilme.add(new JLabel("Linguagem"));
		jpFilme.add(jcbLanguage, new CC().width("50%"));
		jpFilme.add(new JLabel("Linguagem Original"));
		jpFilme.add(jcbOriginalLanguage, new CC().width("50%"));
		jpFilme.add(jbLanguages, new CC().width("30!").wrap());
		jpFilme.add(new JLabel("Classificação"));
		jpFilme.add(jcbRating, new CC().width("60!"));
		jpFilme.add(new JLabel("Característica Especiais"));
		jpFilme.add(jtfSpecialFeatures, new CC().width("150:100%:").wrap());
		jpFilme.add(new JLabel("Custo de reposição ($)"));
		jpFilme.add(jftfReplaceCost, new CC().width("50!"));
		jpFilme.add(jlLastUpdate);
		jpFilme.add(jtfLastUpdate, new CC().width("95!"));
		return jpFilme;
	}
	
	private JPanel createPanelAtores() {
		JPanel jpAtores = new JPanel(new MigLayout());
		jpAtores.add(new JLabel("Atores"));
		jpAtores.add(jcbActor, new CC().width("200:100%:"));
		jpAtores.add(jbAddActor, new CC().width("30!"));
		jpAtores.add(jbRemoveActor, new CC().width("30!"));
		jpAtores.add(jbActors, new CC().width("30!").wrap());
		jpAtores.add(new JScrollPane(jtActors), new CC().width("100%").height("100%").spanX());
		return jpAtores;
	}
	
	private JPanel createPanelCategorias() {
		JPanel jpCategorias = new JPanel(new MigLayout());
		jpCategorias.add(new JLabel("Categorias"));
		jpCategorias.add(jcbCategories, new CC().width("200:100%:"));
		jpCategorias.add(jbAddCategory, new CC().width("30!"));
		jpCategorias.add(jbRemoveCategory, new CC().width("30!"));
		jpCategorias.add(jbCategories, new CC().width("30!").wrap());
		jpCategorias.add(new JScrollPane(jtCategories), new CC().width("100%").height("100%").spanX());
		return jpCategorias;
	}
	
	private JPanel createPanelCadastro() {
		JPanel jpCadastro = new JPanel(new MigLayout());
		jpCadastro.add(jtpFilmActorCategory, new CC().width("100%").height("100%").wrap());
		jpCadastro.add(jbNovo, new CC().width("120!").height("45!").spanX().split().alignX("center"));
		jpCadastro.add(jbSalvarAtualizar, new CC().width("120!").height("45!"));
		jpCadastro.add(jbDeletar, new CC().width("120!").height("45!"));
		return jpCadastro;
	}
	
	private JPanel createPanelPesquisa() {
		JPanel jpPesquisa = new JPanel(new MigLayout());
		jpPesquisa.add(new JScrollPane(jtFilms), new CC().width("100%").height("100%"));
		return jpPesquisa;
	}
	
	private void addComponents() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new MigLayout());
		setTitle("Filmes");
		
		add(jtpCadastroPesquisa, new CC().width("100%").height("100%"));
		
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		
		getLanguages();
		getActors();
		getCategories();
		getData();
	}
	
	private void getLanguages() {
		try {
			List<Language> languagesList = languageConnection.list();
			jcbLanguage.removeAllItems();
			jcbOriginalLanguage.removeAllItems();
			for (Language l : languagesList) {
				jcbLanguage.addItem(l);
				jcbOriginalLanguage.addItem(l);
			}
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar as linguagens do banco!!!");
			e.printStackTrace();
		}
	}
	
	private void getActors() {
		try {
			List<Actor> actorsList = actorConnection.list();
			jcbActor.removeAllItems();
			for (Actor actor : actorsList) {
				jcbActor.addItem(actor);
			}
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar os atores do banco!!!");
			e.printStackTrace();
		}
	}
	
	private void getCategories() {
		try {
			List<Category> categoriesList = categoryConnection.list();
			jcbCategories.removeAllItems();
			for (Category category : categoriesList) {
				jcbCategories.addItem(category);
			}
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar as categorias do banco!!!");
			e.printStackTrace();
		}
	}
	
	private void getData() {
		try {
			tmFilms.setData(filmConnection.list());
		} catch (Exception e) {
			JOptionPaneUtils.showError("Ocorreu um erro ao buscar os dados do banco!!!");
		}
	}
	
	private void clearFields() {
		film = null;
		jlId.setVisible(false);
		jtfId.setText("");
		jtfId.setVisible(false);
		jtfTitle.setText("");
		jtaDescription.setText("");
		jsDuration.setValue(0);
		jsYear.setValue(2015);
		jsRentalDuration.setValue(1);
		jftfRentalRate.setValue(0);
		jcbLanguage.setSelectedIndex(0);
		jcbOriginalLanguage.setSelectedIndex(0);
		jcbRating.setSelectedIndex(0);
		jtfSpecialFeatures.setText("");
		jftfReplaceCost.setValue(0);
		jlLastUpdate.setVisible(false);
		jtfLastUpdate.setText("");
		jtfLastUpdate.setVisible(false);
		jcbActor.setSelectedIndex(0);
		tmActors.clear();
		jcbCategories.setSelectedIndex(0);
		tmCategories.clear();
		jbSalvarAtualizar.setText("Gravar");
		jbDeletar.setEnabled(false);
	}
	
	private void populateView() {
		jlId.setVisible(true);
		jtfId.setText(String.valueOf(film.getFilmId()));
		jtfId.setVisible(true);
		jtfTitle.setText(film.getTitle());
		jtaDescription.setText(film.getDescription());
		jsDuration.setValue(film.getLength());
		jsYear.setValue(film.getYear());
		jsRentalDuration.setValue(film.getRentalDuration());
		jftfRentalRate.setValue(film.getRentalRate());
		jcbLanguage.setSelectedItem(film.getLanguage());
		jcbOriginalLanguage.setSelectedItem(film.getOriginalLanguage());
		jcbRating.setSelectedItem(film.getRating());
		jtfSpecialFeatures.setText(film.getSpecialFeatures());
		jftfReplaceCost.setValue(film.getReplaceCost());
		jlLastUpdate.setVisible(true);
		jtfLastUpdate.setText(film.getLastUpadate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")));
		jtfLastUpdate.setVisible(true);
		jcbActor.setSelectedIndex(0);
		tmActors.setData(film.getActors());
		jcbCategories.setSelectedIndex(0);
		tmCategories.setData(film.getCategories());
		jbSalvarAtualizar.setText("Atualizar");
		jbDeletar.setEnabled(true);
		jtpCadastroPesquisa.setSelectedIndex(0);
		jtpFilmActorCategory.setSelectedIndex(0);
	}
	
	private class ButtonLanguagesActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFLanguage jfLanguage = new JFLanguage();
			jfLanguage.addWindowListener(new LanguageFrameWindowAdapter());
			jfLanguage.setVisible(true);
		}
		
		private class LanguageFrameWindowAdapter extends WindowAdapter {
			@Override
			public void windowClosed(WindowEvent e) {
				getLanguages();
			}
		}
	}
	
	private class ButtonAddActorActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Actor act = (Actor) jcbActor.getSelectedItem();
			if (!tmActors.getData().contains(act)) {
				tmActors.add(act);
			} else {
				JOptionPaneUtils.showWarning("A categoria selecionada já esta vinculada a este filme!");
			}
		}
	}
	
	private class ButtonRemoveActorActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (jtActors.getSelectedRowCount() == 0) {
				JOptionPaneUtils.showWarning("Nenhum ator foi selecionado!\nSelecione um ator da tabela abaixo para remove-lo!");
			} else {
				tmActors.remove(jtActors.getSelectedRow());
			}
		}
	}
	
	private class ButtonActorsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFActor jfActor = new JFActor();
			jfActor.addWindowListener(new ActorFrameWindowAdapter());
			jfActor.setVisible(true);
		}
		
		private class ActorFrameWindowAdapter extends WindowAdapter {
			@Override
			public void windowClosed(WindowEvent e) {
				getActors();
			}
		}
	}
	
	private class ButtonAddCategoryActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Category cat = (Category) jcbCategories.getSelectedItem();
			if (!tmCategories.getData().contains(cat)) {
				tmCategories.add(cat);
			} else {
				JOptionPaneUtils.showWarning("A categoria selecionada já esta vinculada a este filme!");
			}
		}
	}
	
	private class ButtonRemoveCategoryActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (jtCategories.getSelectedRowCount() == 0) {
				JOptionPaneUtils.showWarning("Nenhuma categoria foi selecionada!\nSelecione uma categoria da tabela abaixo para remove-lo!");
			} else {
				tmCategories.remove(jtCategories.getSelectedRow());
			}
		}
	}
	
	private class ButtonCategoriesActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFCategory jfCategory = new JFCategory();
			jfCategory.addWindowListener(new CategoryFrameWindowAdapter());
			jfCategory.setVisible(true);
		}
		
		private class CategoryFrameWindowAdapter extends WindowAdapter {
			@Override
			public void windowClosed(WindowEvent e) {
				getCategories();
			}
		}
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
			String name = jtfTitle.getText().trim();
			if (name.isEmpty()) {
				JOptionPaneUtils.showWarning("O campo 'Nome' é obrigatório!");
				return;
			} else if (JOptionPaneUtils.showQuestionMessage("Deseja " + jbSalvarAtualizar.getText().toLowerCase() + " o filme?")) {
				try {
					if (film == null) {
						film = new Film();
						film.setTitle(name.toUpperCase());
						film.setDescription(jtaDescription.getText());
						film.setLength(((Integer) jsDuration.getValue()).shortValue());
						film.setYear(((Integer) jsYear.getValue()).shortValue());
						film.setRentalDuration(((Integer) jsRentalDuration.getValue()).shortValue());
						film.setRentalRate(jftfRentalRate.getValue() instanceof Integer ? (int) jftfRentalRate.getValue() : (double) jftfRentalRate.getValue());
						film.setLanguage((Language) jcbLanguage.getSelectedItem());
						film.setOriginalLanguage((Language) jcbOriginalLanguage.getSelectedItem());
						film.setRating((Rating) jcbRating.getSelectedItem());
						film.setSpecialFeatures(jtfSpecialFeatures.getText());
						film.setReplaceCost(jftfReplaceCost.getValue() instanceof Integer ? (int) jftfReplaceCost.getValue() : (double) jftfReplaceCost.getValue());
						film.setActors(tmActors.getData());
						film.setCategories(tmCategories.getData());
						filmConnection.insert(film);
					} else {
//						filmConnection.update(film);
						JOptionPaneUtils.showWarning("FUNÇÃO NÃO IMPLEMENTADA!");
						return;
					}
					JOptionPaneUtils.showMessage("Filme " + (jbSalvarAtualizar.getText().equals("Gravar") ?
							"gravado" : "atualizado") + " com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possivel " + jbSalvarAtualizar.getText().toLowerCase() +
							" o filme!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class ButtonDeletarActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPaneUtils.showQuestionMessage("Deseja excluir o filme selecionado?")) {
				try {
					filmConnection.delete(film);
					JOptionPaneUtils.showMessage("Filme excluído com sucesso!");
					clearFields();
					getData();
				} catch (Exception ex) {
					JOptionPaneUtils.showError("Não foi possível excluir a categoria!");
					ex.printStackTrace();
				}
			}
		}
	}
	
	private class TableFilmsMouseAdapter extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			try {
				if (e.getClickCount() > 1) {
					film = filmConnection.get(tmFilms.getValue(jtFilms.getSelectedRow()));
					populateView();
				}
			} catch (Exception ex) {
				JOptionPaneUtils.showError("Ocorreu um erro ao terntar recuperar o filme do banco!!!");
				ex.printStackTrace();
			}
		}
	}
	
}