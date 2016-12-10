package com.company;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Someone on 03.12.2016.
 */
public class Terminal {
    // Список пользователей, которые хранятся в базе терминала
    LinkedList<Client> clients = new LinkedList<>();
    LinkedList<Card> cards = new LinkedList<>();

    public void startTerminal() {
        boolean stillWorking = true;
        while (stillWorking) {
            System.out.println("Выберите действие:");
            System.out.println("1. Воспользоваться личным кабинетом пользователя\n" +
                    "2. Выполнить действия с выбранной картой\n" + "3. Выйти из программы терминала\n");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                switch (scanner.nextInt()) {
                    case 1:
                        clientCabinet();
                        break;
                    case 2:
                        // Работа сразу с картой
                        useSingleCard(cardAuthentication());
                        break;
                    case 3:
                        System.out.println("Спасибо за использование терминала!");
                        stillWorking = false;
                        break;
                    default:
                        System.out.println("Вы ввели неверный номер операции");
                        break;
                }
            }
        }
    }

    // Личный кабинет клиента
    public void clientCabinet() {
        Client client = enterCabinet();
        // Если клиент не прошел аутентификацию или не создал новый личный кабинет, то вход невозможен
        if (client != null) {
            boolean exit = false;
            while (!exit) {
                System.out.println("1. Показать все карты пользователя\n" + "2. Создать новую карту\n"
                        + "3. Работать с конкретной картой\n" + "4. Удалить этого пользователя\n" +
                        "5. Выйти из личного кабинета\n");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                        case 1:
                            client.showAllCards();
                            break;
                        case 2:
                            cards.add(client.addNewCard());
                            break;
                        case 3:
                            // Список карт выводится просто для удобства, чтобы не запоминать номер карты
                            client.showAllCards();
                            System.out.println("Введите номер карты:");
                            scanner = new Scanner(System.in);
                            if (scanner.hasNextLine()) {
                                String cardNumber = scanner.nextLine();
                                useSingleCard(client.ifClientHasCard(cardNumber));
                            }
                            break;
                        case 4:
                            // Пока не реализовано
                            deleteClient(client);
                            exit = true;
                            break;
                        case 5:
                            exit = true;
                            break;
                        default:
                            System.out.println("Не сушествует такого действия");
                            break;
                    }
                }
            }
        }
    }

    // Вход в личный кабинет
    private Client enterCabinet() {
        System.out.println("Выберите действие:\n" + "1. Войти в личный кабинет\n"
                + "2. Создать нового пользователя\n" + "3. Выход");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            switch (scanner.nextInt()) {
                case 1:
                    return accountAuthentication();
                case 2:
                    return createNewClient();
                case 3:
                    System.out.println("Вы решили выйти в главное меню терминала.");
                    break;
            }
        }
        return null;
    }

    // Аутентификация
    private Client accountAuthentication() {
        // Число оставшихся попыток
        int leftTries = 3;
        while (leftTries > 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите логин");
            String enteredLogin = null;
            // Пользователь вводит логин
            if (scanner.hasNextLine()) {
                enteredLogin = scanner.nextLine();
            }
            // Пользователь вводит пароль
            String enteredPassword = null;
            System.out.println("Введите пароль");
            if (scanner.hasNextLine()) {
                enteredPassword = scanner.nextLine();
            }

            for (Client client : clients) {
                if (client.getLogin().equals(enteredLogin)) {
                    if (client.checkPassword(enteredPassword)) {
                        return client;
                    }
                }
            }
            leftTries--;
            System.out.println("Вы неправиьно ввели логин либо пароль. Число оставшихся попыток " + leftTries);
        }
        System.out.println("Вы несколько раз ввели неправильные данные для входа, теперь Ваша " +
                "карта будет заблокирована. Для решения вопроса обратитессь к сотрудникам банка");
        return null;
    }

    private Client createNewClient() {
        Scanner scanner = new Scanner(System.in);
        String clientName = null, clientLogin = null, clientPassword = null;
        System.out.println("Вы решили создать новую учетную запись");
        System.out.println("Введите логин, который Вы будете использовать для входа в личный кабинет:");
        if (scanner.hasNextLine()) {
            clientLogin = scanner.nextLine();
        }
        System.out.println("Введите пароль, который Вы будете использовать для входа в личный кабинет:");
        if (scanner.hasNextLine()) {
            clientPassword = scanner.nextLine();
        }
        System.out.println("Для этого введите имя пользователя:");
        if (scanner.hasNextLine()) {
            clientName = scanner.nextLine();
        }

        Client client = new Client(clientName, clientLogin, clientPassword);
        clients.add(client);
        return client;
    }

    private Card cardAuthentication() {
        // Аутентификация карты
        Scanner scanner = new Scanner(System.in);

        String enteredCardNumber = null;
        int enteredPin = 0;

        System.out.println("Введите номер карты:");
        if (scanner.hasNextLine()) {
            enteredCardNumber = scanner.nextLine();
        }
        System.out.println("Введите PIN-код:");
        if (scanner.hasNextInt()) {
            enteredPin = scanner.nextInt();
        }

        for (Card card : cards) {
            if (card.getCardNumber().equals(enteredCardNumber)) {
                if (card.authenticateCardOwner(enteredPin)) {
                    return card;
                }
            }
        }
        return null;
    }

    public void useSingleCard(Card currentCard) {
        if (currentCard == null) {
            System.out.println("Невозможно идентфицировать карту");
        } else {
            boolean exit = false;
            while (!exit) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Выберите действие:");
                System.out.println("1. Посмотреть лицевой счет\n" +
                        "2. Положить деньги на карту\n" +
                        "3. Снять деньги\n"
                        + "4. Выйти из просмотра");
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println(currentCard.getAccountState());
                            break;
                        case 2:
                            System.out.println("Введите сумму, которую хотите положить:");
                            if (scanner.hasNextInt()) {
                                currentCard.addMoney(scanner.nextInt());
                            }
                            break;
                        case 3:
                            System.out.println("Введите сумму, которую хотите положить:");
                            if (scanner.hasNextInt()) {
                                currentCard.drawMoney(scanner.nextInt());
                            }
                            break;
                        case 4:
                            exit = true;
                            break;
                    }
                }
            }
        }
    }


    private void deleteClient(Client client) {
        clients.remove(client);
        System.out.println("Клиент удален из базы терминала");
    }
}