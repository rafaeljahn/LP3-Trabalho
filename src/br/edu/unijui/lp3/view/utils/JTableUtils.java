package br.edu.unijui.lp3.view.utils;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.towel.el.FieldResolver;
import com.towel.el.factory.FieldResolverFactory;
import com.towel.swing.table.ObjectTableModel;

import br.edu.unijui.lp3.model.Actor;
import br.edu.unijui.lp3.model.Category;
import br.edu.unijui.lp3.model.Film;
import br.edu.unijui.lp3.model.Language;

public abstract class JTableUtils {
	
	public static JTable createTableActor() {
		FieldResolverFactory frFac = new FieldResolverFactory(Actor.class);
		FieldResolver frId = frFac.createResolver("actorId", "ID");
		FieldResolver frFirsName = frFac.createResolver("firstName", "Nome");
		FieldResolver frLastName = frFac.createResolver("lastName", "Sobrenome");
		ObjectTableModel<Actor> tmActors = new ObjectTableModel<Actor>(new FieldResolver[] {frId, frFirsName, frLastName});
		JTable jtActors = new JTable(tmActors);
		jtActors.getTableHeader().setReorderingAllowed(false);
		jtActors.getColumnModel().getColumn(0).setMinWidth(50);
		jtActors.getColumnModel().getColumn(0).setMaxWidth(50);
		jtActors.getColumnModel().getColumn(1).setMinWidth(150);
		jtActors.getColumnModel().getColumn(2).setMinWidth(150);
		jtActors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return jtActors;
	}
	
	public static JTable createTableCategory() {
		FieldResolverFactory frFac = new FieldResolverFactory(Category.class);
		FieldResolver frId = frFac.createResolver("categoryId", "ID");
		FieldResolver frName = frFac.createResolver("name", "Nome");
		ObjectTableModel<Category> tmCategories = new ObjectTableModel<Category>(new FieldResolver[] {frId, frName});
		JTable jtCategories = new JTable(tmCategories);
		jtCategories.getTableHeader().setReorderingAllowed(false);
		jtCategories.getColumnModel().getColumn(0).setMinWidth(50);
		jtCategories.getColumnModel().getColumn(0).setMaxWidth(50);
		jtCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return jtCategories;
	}
	
	public static JTable createTableFilms() {
		FieldResolverFactory frFac = new FieldResolverFactory(Film.class);
		FieldResolver frId = frFac.createResolver("filmId", "ID");
		FieldResolver frName = frFac.createResolver("title", "Titulo");
		FieldResolver frYear = frFac.createResolver("year", "Ano");
		FieldResolver frLanguage = frFac.createResolver("language.name", "Linguagem");
		FieldResolver frLength = frFac.createResolver("length", "Duração");
		FieldResolver frRating = frFac.createResolver("rating", "Class.");
		ObjectTableModel<Film> tmFilms = new ObjectTableModel<Film>(new FieldResolver[] {frId, frName, frYear, frLength, frLanguage, frRating});
		JTable jtFilms = new JTable(tmFilms);
		jtFilms.getTableHeader().setReorderingAllowed(false);
		jtFilms.getColumnModel().getColumn(0).setMinWidth(50);
		jtFilms.getColumnModel().getColumn(0).setMaxWidth(50);
		jtFilms.getColumnModel().getColumn(1).setMinWidth(150);
		jtFilms.getColumnModel().getColumn(2).setMinWidth(30);
		jtFilms.getColumnModel().getColumn(2).setMaxWidth(30);
		jtFilms.getColumnModel().getColumn(3).setMinWidth(50);
		jtFilms.getColumnModel().getColumn(3).setMaxWidth(50);
		jtFilms.getColumnModel().getColumn(4).setMinWidth(80);
		jtFilms.getColumnModel().getColumn(4).setMaxWidth(80);
		jtFilms.getColumnModel().getColumn(5).setMinWidth(40);
		jtFilms.getColumnModel().getColumn(5).setMaxWidth(40);
		jtFilms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return jtFilms;
	}
	
	public static JTable createTableLanguages() {
		FieldResolverFactory frFac = new FieldResolverFactory(Language.class);
		FieldResolver frId = frFac.createResolver("languageId", "ID");
		FieldResolver frName = frFac.createResolver("name", "Nome");
		ObjectTableModel<Language> tmLanguages = new ObjectTableModel<Language>(new FieldResolver[] {frId, frName});
		JTable jtLanguages = new JTable(tmLanguages);
		jtLanguages.getTableHeader().setReorderingAllowed(false);
		jtLanguages.getColumnModel().getColumn(0).setMinWidth(50);
		jtLanguages.getColumnModel().getColumn(0).setMaxWidth(50);
		jtLanguages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return jtLanguages;
	}
	
}