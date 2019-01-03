package com.cts.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cts.dao.HibernateDao;
import com.cts.entity.Book;
import com.cts.entity.Subject;
import com.cts.util.JPAUtil;

public class MainClass {
	static Scanner in = null;

	public static void main(String[] args) {
		String choice;
		HibernateDao hibernateDao = new HibernateDao();
		Book book;
		List<Book> books;
		Subject subject;
		List<Subject> subjects;
		try {
			in = new Scanner(System.in);
			do {
				long id = 0;
				System.out.println();
				System.out.println("a.  Add a Book");
				System.out.println("b.  Delete a Subject");
				System.out.println("c.  Delete a book");
				System.out.println("d.  Search for a book");
				System.out.println("e.  Search for a subject");
				System.out.println("f.  Sort Book By Title");
				System.out.println("g.  Sort Subject By Subject Title");
				System.out.println("h.  Sort Books By Publish Date");
				System.out.println("i.  Exit");
				System.out.println("--------------------------");
				System.out.println("Enter your choice");
				choice = in.next();    

				switch(choice) {
				case "a":
					subjects = hibernateDao.getAllSubjects();
					book = createBook(subjects, hibernateDao);
					hibernateDao.add(book);
					System.out.println("Book with id: "+book.getBookId() + " has been added successfully");
					break;
				case "b":
					System.out.println("Enter subject id to delete");
					id = in.nextLong();
					subject = hibernateDao.findSubject(id);
					if(subject != null) {
						hibernateDao.delete(subject);
						System.out.println("Subject with id: "+ id + " has been deleted successfully");
					}
					else {
						System.out.println("No Subject found with id: "+ id );
					}
					break;
				case "c":
					System.out.println("Enter book id to delete");
					id = in.nextLong();
					book = hibernateDao.findBook(id);
					if(book != null) {
						hibernateDao.delete(book);
						System.out.println("Book with id: "+ id + " has been deleted successfully");
					}
					else {
						System.out.println("No Book found with id: "+ id );
					}
					break;
				case "d":
					System.out.println("Enter book id to search");
					id = in.nextLong();
					book = hibernateDao.findBook(id);
					if(book != null) {
						System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
						System.out.format("\n%10d %15.2f %15s %15d %15s \n", book.getBookId(), book.getPrice(), book.getTitle(), book.getVolume(), book.getPublishDate());
						subject = book.getSubject();
						System.out.println("Subject Details are");
						System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
						System.out.format("\n%10d %20s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
					}
					else {
						System.out.println("Unable to find Book with id "+ id);
					}
					break;
				case "e":
					System.out.println("Enter subject id to search");
					id = in.nextLong();
					subject = hibernateDao.findSubject(id);
					if(subject != null) {
						System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
						System.out.format("\n%10d %20s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
					}
					break;	
				case "f":
					books = hibernateDao.getAllBooksSortByTitle();
					if(books !=null && !books.isEmpty()) {
						System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
						for(Book bk : books) {
							System.out.format("\n%10d %15.2f %15s %15d %15s \n", bk.getBookId(), bk.getPrice(), bk.getTitle(), bk.getVolume(), bk.getPublishDate());
						}
					}
					break;
				case "g":
					subjects = hibernateDao.getAllSubjectsSortBySubTitle();
					if(subjects != null && !subjects.isEmpty()) {
						System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
						for(Subject sub : subjects) {
							System.out.format("\n%10d %20s %15d \n", sub.getSubjectId(), sub.getSubtitle(), sub.getDurationInHours());
						}
					}
					break;
				case "h":
					books = hibernateDao.getAllBooksSortByPublishDate();
					if(books !=null && !books.isEmpty()) {
						System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
						for(Book bk : books) {
							System.out.format("\n%10d %15.2f %15s %15d %15s \n", bk.getBookId(), bk.getPrice(), bk.getTitle(), bk.getVolume(), bk.getPublishDate());
						}
					}
					break;
				case "i":
					System.out.println("Thank You");
					JPAUtil.shutdown();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Option.");
				}
			}while (!"i".equalsIgnoreCase(choice));
		}

		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				in.close();
			}
		}
	}


	private static Book createBook(List<Subject> subjects, HibernateDao hibernateDao) {
		Book book = new Book();
		System.out.println("Adding a Book");
		System.out.println("Please enter Price");
		book.setPrice(in.nextDouble());
		System.out.println("Please enter Title");
		book.setTitle(in.next());
		System.out.println("Please enter Volume");
		book.setVolume(in.nextInt());
		book.setPublishDate(LocalDate.now());
		Map<Long, Subject> subjectMap = new HashMap<>();
		if(subjects != null && !subjects.isEmpty()) {
			System.out.println("All available subject details");
			System.out.format("\n%10s %20s %15s ", "Id", "SubTitle", "DurationInHours");
			for(Subject subject : subjects) {
				System.out.format("\n%10d %20s %15d \n", subject.getSubjectId(), subject.getSubtitle(), subject.getDurationInHours());
				subjectMap.put(subject.getSubjectId(), subject);
			}
		}
		System.out.println("Please enter subject id to enter as a reference (enter 0 if you want to create a subject)");
		long id = in.nextLong();
		if(id > 0) {
			book.setSubject(hibernateDao.findSubject(id));
		}
		else {
			book.setSubject(createSubject());
		}
		return book;
	}

	private static Subject createSubject() {
		Subject subject = new Subject();
		System.out.println("Adding a Subject");
		System.out.println("Please enter subtitle");
		subject.setSubtitle(in.next());
		System.out.println("Please enter durationInHours");
		subject.setDurationInHours(in.nextInt());
		HibernateDao hibernateDao = new HibernateDao();
		hibernateDao.add(subject);
		return subject;
	}
}

