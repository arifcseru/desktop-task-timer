package com.timer.coagent;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Announcer extends WindowActivityManager  implements ActionListener{
	private static final Set<String> existingTexts = new HashSet<>();
	public static UxBoxDrawer boxDrawer = null;
	private static String randomString = "Welcome to reminder...";
	public static JFrame frame = null;
	public static Properties quoteProperties = null;
	public static Properties configProperties = null;
	public static List<String> restrictedKeywords = null;

	static {
		quoteProperties = loadProperties("quotes.properties");
		configProperties = loadProperties("config.properties");
		restrictedKeywords = Arrays.asList(configProperties.getProperty("restricted_keywords").split(","));
	}

//	public static void main(String[] args) {
//		Announcer announcer = new Announcer();
//		boxDrawer = new UxBoxDrawer(randomString);
//
//		SwingUtilities.invokeLater(() -> {
//			frame = new JFrame("Reminder");
//			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//			frame.getContentPane().add(boxDrawer);
//
//			// Customize frame properties
//			frame.setUndecorated(true); // Remove title bar and border
//			frame.setResizable(false); // Disable resizing
//			frame.pack();
//
//			// Position the frame at the top middle of the screen
//			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//			int x = ((screenSize.width - frame.getWidth()) / 2) + 300;
//			frame.setLocation(x, 0);
//			frame.setAlwaysOnTop(true);
//			hideAppIcon();
//
//			frame.setVisible(true);
//			makeFrameDraggable(frame);
//
//			frame.addWindowListener(new WindowAdapter() {
//				@Override
//				public void windowClosing(WindowEvent e) {
//					int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to close the application?",
//							"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
//							null);
//					if (confirm == JOptionPane.YES_OPTION) {
//						frame.hide();
//					}
//				}
//			});
//		});
//
//		while (true) {
//			boxDrawer.setRandomString(randomString);
//
//			try {
//				String delay = configProperties.getProperty("delay");
//
//				Thread.sleep(Integer.valueOf(delay) * 1000); // delay in seconds
//				String activityMessage = announcer.checkActiveWindowActivity();
//
//				if (!activityMessage.trim().isEmpty() && activityMessage.contains("chrome")) {
//					announcer.checkUsersChromeActivity();
//				} else if (!activityMessage.trim().isEmpty() && activityMessage.contains("firefox")) {
//					announcer.checkUsersFirefoxActivity();
//				} else if (!activityMessage.trim().isEmpty()) {
//					randomString = activityMessage;
//					boxDrawer.setRandomStringDanger(activityMessage);
//				} else {
//					String randomSentence = getRandomSentence();
//					boxDrawer.setRandomString(randomSentence);
//				}
//
//			} catch (InterruptedException e) {
//				System.err.println("Sleep interrupted: " + e.getMessage());
//			}
//		}
//
//	}

	public String checkActiveWindowActivity() {

		String activeWindowTitle = getActiveWindowTitle();
		String activeAppName = getActiveApplicationName();
//		System.out.println("activeWindowTitle: " + activeWindowTitle);
//		System.out.println("activeAppName: " + activeAppName);

		if (containsRestrictedKeywords(activeWindowTitle, restrictedKeywords)) {
			return "Accountable yourself";
		} else if (activeWindowTitle.contains("eclipse") || activeAppName.contains("eclipse.exe")) {
			return "Happy Coding...";
		} else if (activeWindowTitle.contains("Google") || activeAppName.contains("firefox.exe")) {
			return "google";
		} else if (activeAppName.contains("sqldeveloper")) {
			return "Keep focus on requirements...";
		} else if (activeAppName.contains("nodepad++")) {
			return "Plan things..";
		} else if (activeWindowTitle.contains(".pdf")) {
			return "Happy Reading...";
		} else if (activeAppName.contains("MobaXterm")) {
			return "Its devops time!!!";
		} else if (activeAppName.contains("Postman")) {
			return "Its devops time!!!";
		} else if (activeAppName.contains("explorer.exe")) {
			return "Organize your files!!!";
		} else if (activeWindowTitle.contains("localhost")) {
			return "Test your implementation!!";
		} else if (activeWindowTitle.contains("LeetCode")) {
			return "Problem solving is very essential..";
		} else if (activeWindowTitle.contains("Zimbra") || activeWindowTitle.contains("Gmail")) {
			return "Focus on solutions..";
		} else if (activeAppName.contains("chrome.exe")) {
			return "chrome";
		} else if (activeAppName.contains("firefox.exe")) {
			return "firefox";
		} else if (activeAppName.contains("ApplicationFrameHost.exe")) {
			return "Stay focused on productive tasks ";
		}

		return "";
//		System.out.println("activeWindowTitle: " + activeWindowTitle);
//		System.out.println("activeAppName: " + activeAppName);
	}

	private void stopFirefox() {
		String os = System.getProperty("os.name").toLowerCase();
		String command = "";

		if (os.contains("win")) {
			// Command for Windows
			command = "taskkill /F /IM firefox.exe";
		} else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
			// Command for macOS/Linux
			command = "pkill -f firefox";
		} else {
			System.out.println("Unsupported operating system.");
			return;
		}

		try {
			// Execute the command to kill Firefox
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			System.out.print("ff_terminated.");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void checkUsersFirefoxActivity() {
		System.out.println("\nff..");
		boolean databaseChecked = false;
		while (true) {
			String activeWindowTitle = getActiveWindowTitle();
			String activeAppName = getActiveApplicationName();

			if (!activeAppName.contains("firefox")) {
				System.out.print("ff_exit, ");
				break;
			}
//			System.out.println("restrictedKeywords[2]: " + restrictedKeywords.get(2));
//			System.out.println("activeWindowTitle: " + activeWindowTitle);

			if (containsRestrictedKeywords(activeWindowTitle, restrictedKeywords)) {
				randomString = "Accountable yourself";
				boxDrawer.setRandomString(randomString);
				stopFirefox();
			} else if (activeWindowTitle.contains("youtube.com")) {
				String randomSentence = "Please be productive.";
				boxDrawer.setRandomString(randomSentence);
				randomString = randomSentence;
				if (!frame.isShowing()) {
					// frame.show(); // disabled due to other app is making visible
				}
			} else if (activeWindowTitle.contains("icsbook")) {
				String randomSentence = "Earn knowledge.";
				boxDrawer.setRandomString(randomSentence);
				randomString = randomSentence;
				if (frame.isShowing()) {
					frame.hide();
				}
			} else if (activeWindowTitle.contains("Convay") || activeWindowTitle.contains("Zoom")
					|| activeWindowTitle.contains("Teams")) {
//				String randomSentence = "Meeting time...";
//				boxDrawer.setRandomString(randomSentence);
//				randomString = randomSentence;
//				System.exit(1);
				if (frame.isShowing()) {
					frame.hide();
				}
			} else if (activeWindowTitle.contains("prothomalo.com")) {
				String randomSentence = "Lets get back to work.";
				boxDrawer.setRandomString(randomSentence);
				randomString = randomSentence;
			} else if (activeWindowTitle.contains("images")) {
				String randomSentence = "Lets get back to work.";
				boxDrawer.setRandomString(randomSentence);
				randomString = randomSentence;
				if (!frame.isShowing()) {
					// frame.show(); // disabled due to other app is making visible
				}
			} else if (activeWindowTitle.contains("google.com") || activeWindowTitle.contains("Google")) {
				String randomSentence = "Lets get back to work.";
				boxDrawer.setRandomString(randomSentence);
				randomString = randomSentence;
				if (!frame.isShowing()) {
					// frame.show(); // disabled due to other app is making visible
				}
			} else if (activeWindowTitle.contains("somewhereinblog")) {
				String randomSentence = "Lets explore...";
				boxDrawer.setRandomString(randomSentence);
				randomString = randomSentence;
			} else if (activeWindowTitle.contains("leetcode.com")) {
				String randomSentence = "Happy Coding...";
				boxDrawer.setRandomStringSuccess(randomSentence);
				randomString = randomSentence;
			} else if (activeWindowTitle.contains("stackoverflow")) {
				String randomSentence = "Happy Coding...";
				boxDrawer.setRandomStringSuccess(randomSentence);
				randomString = randomSentence;
			} else if (activeWindowTitle.contains("github.com")) {
				String randomSentence = "Happy Coding...";
				boxDrawer.setRandomStringSuccess(randomSentence);
				randomString = randomSentence;
			} else if (activeWindowTitle.contains("chatgpt.com")) {
				String randomSentence = "Explore knowledge...";
				boxDrawer.setRandomStringInfo(randomSentence);
				randomString = randomSentence;
			} else {
				randomString = "Productivity brings success.";
				boxDrawer.setRandomString(randomString);
				if (!databaseChecked) {
					System.out.print("ff_db_try, ");
					Path firefoxPath = Paths.get(System.getenv("APPDATA"), "Mozilla\\Firefox\\Profiles");
					if (Files.exists(firefoxPath) && Files.isDirectory(firefoxPath)) {
						try {
							Files.walk(firefoxPath).filter(path -> path.endsWith("places.sqlite")).findFirst()
									.ifPresent(Announcer::readFirefoxHistoryFile);
							databaseChecked = true;
							System.out.print("ff_db_done, ");
						} catch (IOException e) {
							System.err.println("Error reading Firefox directory: " + e.getMessage());
						}
					} else {
						System.out.println("Firefox directory does not exist.");
					}
				}
			}

			String delay = configProperties.getProperty("delay");

			try {
				Thread.sleep(Integer.valueOf(delay) * 100);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			} // delay in seconds
		}

	}

	public void checkUsersChromeActivity() {
		String activeWindowTitle = getActiveWindowTitle();
//		String activeAppName = getActiveApplicationName();

		if (containsRestrictedKeywords(activeWindowTitle, restrictedKeywords)) {
			randomString = "Accountable yourself";
			boxDrawer.setRandomString(randomString);
		} else {
			String chromeHistoryFile = System.getProperty("user.home")
					+ "/AppData/Local/Google/Chrome/User Data/Default/History";
			Path chromePath = Paths.get(System.getProperty("user.home"),
					"AppData\\Local\\Google\\Chrome\\User Data\\Default\\History");
			if (Files.exists(chromePath)) {
				Path tempHistoryFile = null;
				try {
					tempHistoryFile = Files.createTempFile("chrome_history", ".sqlite");
					Files.copy(Paths.get(chromeHistoryFile), tempHistoryFile, StandardCopyOption.REPLACE_EXISTING);
					// readChromeHistoryFile(chromePath);
					readChromeHistoryFile(tempHistoryFile);

				} catch (IOException e) {
					System.err.println("Error reading history file: " + e.getMessage());
					if (tempHistoryFile != null) {
						try {
							Files.delete(tempHistoryFile);
						} catch (IOException ex) {
							System.err.println("Error deleting temp history file: " + ex.getMessage());
						}
					}

				}

			} else {
				System.out.println("Chrome history file does not exist.");
			}
		}

	}

	private static void readFirefoxHistoryFile(Path sqliteFilePath) {
		readLastFewHistoryData(sqliteFilePath, "moz_places", "url", "title", "last_visit_date");
	}

	private static void readChromeHistoryFile(Path sqliteFilePath) {
		readLastFewHistoryData(sqliteFilePath, "urls", "url", "title", "last_visit_time");
	}

	public static String getRandomSentence() {
		int quoteCount = quoteProperties.size();
		int randomIndex = new Random().nextInt(quoteCount) + 1; // Generate a random index between 1 and quoteCount
		return quoteProperties.getProperty("quote" + randomIndex);
	}

	private static boolean visitedWebsiteInLastFewSeconds(String webAddress) {
		// Example for Firefox history file, adapt for Chrome if needed
		String firefoxHistoryFile = System.getProperty("user.home") + "/AppData/Roaming/Mozilla/Firefox/Profiles/";
		try {
			Path path = Files.walk(Paths.get(firefoxHistoryFile)).filter(p -> p.toString().endsWith("places.sqlite"))
					.findFirst().orElseThrow(() -> new RuntimeException("Firefox history file not found"));

			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + path.toString());
			String query = "SELECT url, visit_date FROM moz_historyvisits INNER JOIN moz_places ON moz_places.id = moz_historyvisits.place_id WHERE url LIKE '%"
					+ webAddress + "%' AND visit_date > ?";

			PreparedStatement statement = connection.prepareStatement(query);
			long currentTime = System.currentTimeMillis();
			long lastFewSecondsAgo = currentTime - 30 * 1000;
			statement.setLong(1, lastFewSecondsAgo * 1000);

			ResultSet resultSet = statement.executeQuery();
			boolean visited = resultSet.next();
			resultSet.close();
			statement.close();
			connection.close();
			return visited;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void readLastFewHistoryData(Path sqliteFilePath, String tableName, String urlColumnName,
			String titleColumnName, String lastVisitTimeField) {
		try {
			// Load the SQLite JDBC driver
			Class.forName("org.sqlite.JDBC");

			String url = "jdbc:sqlite:" + sqliteFilePath;
			String outputFilePath = "D:\\backup_files\\quote_reminder\\bw_info.txt";

			try (Connection conn = DriverManager.getConnection(url);
					BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
				if (conn != null) {

//					String sql = "SELECT " + urlColumnName + ", " + titleColumnName + " FROM " + tableName + " WHERE "
//							+ titleColumnName + " IS NOT NULL;";

					String query = "SELECT " + urlColumnName + ", " + titleColumnName + ", " + lastVisitTimeField
							+ " AS visit_date FROM " + tableName + " " + "WHERE " + titleColumnName
							+ " IS NOT NULL ORDER BY last_visit_date DESC limit 20";
//					System.out.println("query: " + query);
					try (Statement stmt = conn.createStatement();
							PreparedStatement pstmt = conn.prepareStatement(query)) {
						// pstmt.setString(1, eightHoursAgoTimestamp);

						ResultSet rs = stmt.executeQuery(query);
						int totalFound = 0;
						while (rs.next()) {
							String urlAddress = rs.getString(urlColumnName);
							String title = rs.getString(titleColumnName);
							Timestamp visitedDate = (rs.getTimestamp("visit_date"));
							long actualVisitedDate = visitedDate.getTime() / 1000;

							SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Date date = new Date(Long.parseLong(String.valueOf(actualVisitedDate)));

							String visitDateStr = sf.format(date);

							String modifiedTitle = replaceRestrictedKeywordsWithAsterisks(title, restrictedKeywords);
//							System.out.println(visitDateStr + ": " + title);

							if (!existingTexts.contains(modifiedTitle)
									&& (containsRestrictedKeywords(urlAddress, restrictedKeywords)
											|| containsRestrictedKeywords(title, restrictedKeywords))) {

								writer.write(visitDateStr + " : " + modifiedTitle);
								writer.newLine();

								totalFound++;
								existingTexts.add(modifiedTitle);
								String randomSentence = "Accountable yourself";
								boxDrawer.setRandomString(randomSentence);
								randomString = randomSentence;

								if (!frame.isShowing()) {
									// frame.show(); // disabled due to other app is running
								}

							}
//							else if (tableName.equalsIgnoreCase("moz_places")) {
//								String randomSentence = getRandomSentence();
//								randomString = randomSentence;
////								randomString = "Productivity leads to success.";
//								boxDrawer.setRandomString(randomString);
//							}
						}

//						System.out.println("total new found: " + totalFound);
					}
					conn.close();
				}
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			System.err.println("Error reading history file: " + e.getMessage());
		}
	}

//	private static Properties loadProperties(String fileName) {
//		Properties properties = new Properties();
//		try (InputStream input = UxBoxDrawer.class.getClassLoader().getResourceAsStream(fileName)) {
//			if (input == null) {
//				System.err.println("Unable to find " + fileName);
//				return properties;
//			}
//			properties.load(input);
//		} catch (IOException e) {
//			System.err.println("Error loading properties file: " + e.getMessage());
//		}
//		return properties;
//	}

	private static Properties loadProperties(String fileName) {
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream(fileName)) {
			properties.load(input);
		} catch (IOException e) {
			System.err.println("Error loading properties file: " + e.getMessage());
		}
		return properties;
	}

	private static boolean containsRestrictedKeywords(String text, List<String> keywords) {
		if (text == null)
			return false;
		String lowerText = text.toLowerCase();
		boolean doesContain = false;
		for (String keyword : keywords) {
//			System.out.println("keyword: " + keyword + ", text: " + text);
			if (containsFullWord(lowerText, keyword.trim())) {
				doesContain = true;
				break;
			}
		}
		// doesContain = keywords.stream().anyMatch(lowerText::contains);
//		System.out.println("text: " + text + ", doesContain: " + doesContain);
		return doesContain;
	}

	public static boolean containsFullWord(String text, String word) {
		// Define the regular expression pattern to match the full word
		String patternString = "\\b" + Pattern.quote(word) + "\\b";
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);

		// Return true if the word is found as a full word in the text
		return matcher.find();
	}

	private static String replaceRestrictedKeywordsWithAsterisks(String text, List<String> keywords) {
		if (text == null)
			return "";
		String modifiedText = text.toLowerCase();
		for (String keyword : keywords) {
			modifiedText = modifiedText.replaceAll(keyword.toLowerCase(), generateAsterisks(keyword.length()));
		}
		return modifiedText;
	}

	private static String generateAsterisks(int length) {
		char[] asterisks = new char[length];
		Arrays.fill(asterisks, '*');
		return new String(asterisks);
	}

	private static void makeFrameDraggable(JFrame frame) {
		UxBoxDrawer uxBoxDrawer = (UxBoxDrawer) frame.getContentPane().getComponent(0);
		MouseDragListener mouseListener = new MouseDragListener(frame, uxBoxDrawer);
		frame.addMouseListener(mouseListener);
		frame.addMouseMotionListener(mouseListener);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public static class MouseDragListener extends MouseAdapter {
		private final JFrame frame;
		private final UxBoxDrawer uxBoxDrawer;
		private Point initialClick;

		public MouseDragListener(JFrame frame, UxBoxDrawer uxBoxDrawer) {
			this.frame = frame;
			this.uxBoxDrawer = uxBoxDrawer;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();
			uxBoxDrawer.getComponentAt(initialClick);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// Get the location of the window
			int thisX = frame.getLocation().x;
			int thisY = frame.getLocation().y;

			// Determine how much the mouse moved since the initial click
			int xMoved = e.getX() - initialClick.x;
			int yMoved = e.getY() - initialClick.y;

			// Move the window to this position
			int X = thisX + xMoved;
			int Y = thisY + yMoved;
			frame.setLocation(X, Y);
		}
	}

	private static void hideAppIcon() {
		if (SystemTray.isSupported()) {
//			System.out.println("hide App icon1");
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // Provide an icon image if needed
			TrayIcon trayIcon = new TrayIcon(image, "Hidden Icon");

			try {
				tray.add(trayIcon);
				tray.remove(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

}
