import java.util.*;

public class CapitalCityGame {
    
    // Game data structure
    private static final Map<String, Map<String, String>> gameData = new HashMap<>();
    private static final int QUESTIONS_PER_ROUND = 10;
    
    // Game state
    private String difficulty;
    private List<String> questions;
    private int currentQuestionIndex;
    private int score;
    private int pointsPerCorrect;
    private Scanner scanner;
    
    // Initialize game data
    static {
        // Easy difficulty
        Map<String, String> easy = new HashMap<>();
        easy.put("United States", "Washington D.C.");
        easy.put("United Kingdom", "London");
        easy.put("France", "Paris");
        easy.put("Germany", "Berlin");
        easy.put("Italy", "Rome");
        easy.put("Spain", "Madrid");
        easy.put("Canada", "Ottawa");
        easy.put("Australia", "Canberra");
        easy.put("Japan", "Tokyo");
        easy.put("China", "Beijing");
        gameData.put("easy", easy);
        
        // Medium difficulty
        Map<String, String> medium = new HashMap<>();
        medium.put("Netherlands", "Amsterdam");
        medium.put("Belgium", "Brussels");
        medium.put("Switzerland", "Bern");
        medium.put("Austria", "Vienna");
        medium.put("Portugal", "Lisbon");
        medium.put("Greece", "Athens");
        medium.put("Poland", "Warsaw");
        medium.put("Norway", "Oslo");
        medium.put("Sweden", "Stockholm");
        medium.put("Denmark", "Copenhagen");
        gameData.put("medium", medium);
        
        // Hard difficulty
        Map<String, String> hard = new HashMap<>();
        hard.put("Andorra", "Andorra la Vella");
        hard.put("Antigua and Barbuda", "St. John's");
        hard.put("Bahamas", "Nassau");
        hard.put("Barbados", "Bridgetown");
        hard.put("Brunei", "Bandar Seri Begawan");
        hard.put("Burundi", "Gitega");
        hard.put("Cape Verde", "Praia");
        hard.put("Central African Republic", "Bangui");
        hard.put("Chad", "N'Djamena");
        hard.put("Comoros", "Moroni");
        gameData.put("hard", hard);
    }
    
    public CapitalCityGame() {
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     🌍 CAPITAL CITY GAME 🌍          ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Test your world capitals knowledge!\n");
        
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    startGame("easy");
                    break;
                case "2":
                    startGame("medium");
                    break;
                case "3":
                    startGame("hard");
                    break;
                case "4":
                    System.out.println("\nThanks for playing! Goodbye! 👋");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║            SELECT DIFFICULTY         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. 🟢 Easy   (1 point each)          ║");
        System.out.println("║ 2. 🟡 Medium (2 points each)         ║");
        System.out.println("║ 3. 🔴 Hard   (3 points each)         ║");
        System.out.println("║ 4. ❌ Exit                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Enter your choice (1-4): ");
    }
    
    private void startGame(String difficulty) {
        this.difficulty = difficulty;
        this.score = 0;
        this.currentQuestionIndex = 0;
        this.pointsPerCorrect = difficulty.equals("easy") ? 1 : 
                                difficulty.equals("medium") ? 2 : 3;
        
        // Get all countries for selected difficulty
        Set<String> countries = gameData.get(difficulty).keySet();
        List<String> countryList = new ArrayList<>(countries);
        
        // Shuffle and select 10 random questions
        Collections.shuffle(countryList);
        this.questions = countryList.subList(0, Math.min(QUESTIONS_PER_ROUND, countryList.size()));
        
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.printf("║  %s DIFFICULTY - %d QUESTIONS  ║\n", 
                         difficulty.toUpperCase(), QUESTIONS_PER_ROUND);
        System.out.println("╚══════════════════════════════════════╝\n");
        
        playGame();
    }
    
    private void playGame() {
        while (currentQuestionIndex < questions.size()) {
            showProgress();
            askQuestion();
        }
        endGame();
    }
    
    private void showProgress() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.printf("║  Score: %-3d  |  Question: %d/%d    ║\n", 
                         score, currentQuestionIndex + 1, questions.size());
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    private void askQuestion() {
        String country = questions.get(currentQuestionIndex);
        String correctAnswer = gameData.get(difficulty).get(country);
        
        System.out.printf("\n❓ What is the capital of %s?\n", country);
        System.out.print("➡️  Your answer: ");
        
        String userAnswer = scanner.nextLine().trim();
        
        if (isCorrect(userAnswer, correctAnswer)) {
            score += pointsPerCorrect;
            System.out.printf("✅ Correct! +%d point%s!\n", 
                            pointsPerCorrect, pointsPerCorrect > 1 ? "s" : "");
        } else {
            System.out.printf("❌ Incorrect! The correct answer is: %s\n", correctAnswer);
        }
        
        currentQuestionIndex++;
        
        if (currentQuestionIndex < questions.size()) {
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private boolean isCorrect(String userAnswer, String correctAnswer) {
        String cleanUser = cleanString(userAnswer);
        String cleanCorrect = cleanString(correctAnswer);
        return cleanUser.equals(cleanCorrect);
    }
    
    private String cleanString(String s) {
        return s.toLowerCase()
                .replaceAll("[^a-zA-Z]", "");
    }
    
    private void endGame() {
        int maxScore = questions.size() * pointsPerCorrect;
        double percentage = (double) score / maxScore;
        
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║           🎯 GAME OVER! 🎯            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║  Final Score: %d/%d                  ║\n", score, maxScore);
        System.out.printf("║  Percentage: %.1f%%                   ║\n", percentage * 100);
        
        // Show rating
        String rating;
        if (percentage == 1.0) {
            rating = "🏆 PERFECT! Geography Genius!";
        } else if (percentage >= 0.8) {
            rating = "🌟 Excellent! Almost perfect!";
        } else if (percentage >= 0.6) {
            rating = "👍 Good job! Keep practicing!";
        } else if (percentage >= 0.4) {
            rating = "📚 Not bad! Study a bit more!";
        } else {
            rating = "🗺️ Keep exploring the world!";
        }
        
        System.out.printf("║  %-30s║\n", rating);
        System.out.println("╚══════════════════════════════════════╝\n");
        
        System.out.print("Play again? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        
        if (choice.equals("y")) {
            System.out.println();
        } else {
            System.out.println("\nThanks for playing! Goodbye! 👋");
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        CapitalCityGame game = new CapitalCityGame();
        game.start();
    }
}