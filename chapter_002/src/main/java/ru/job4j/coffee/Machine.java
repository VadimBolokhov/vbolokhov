package ru.job4j.coffee;

import java.util.Arrays;

/**
 * Кофе-машина.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Machine {
    /** Массив купюр, которые принимает автомат */
    private final int[] banknotes = {50, 100, 200, 500, 1000, 2000, 5000};
    /** Массив монет, которыми автомат выдаёт сдачу */
    private final int[] coins = {10, 5, 2, 1};

    /**
     * Производит рассчёт сдачи при покупки кофе.
     * Сдача выдается монетами достоинством 1, 2, 5 и 10 рублей.
     * @param value купюра
     * @param price цена
     * @return массив монет
     * @throws IncorrectValueException если купюра задана неверно,
     * или денег недостаточно для покупки
     */
    public int[] changes(int value, int price) throws IncorrectValueException {
        if (!this.validValue(value)) {
            throw new IncorrectValueException("Такую купюру автомат не принимает");
        }
        if (value < price) {
            throw new IncorrectValueException("Недостаточно денег");
        }
        int[] result = new int[0];
        int change = value - price;
        int count;
        int index = 0;
        while (change != 0) {
            count = change / this.coins[index];
            if (count > 0) {
                result = this.merge(result, this.addCoins(this.coins[index], count));
            }
            change %= this.coins[index++];
        }
        return result;
    }

    /**
     * Создаёт массив монет одного достоинства заданной длины
     * @param coin достоинство монеты
     * @param count количество монет
     * @return массив монет
     */
    private int[] addCoins(int coin, int count) {
        int[] result = new int[count];
        Arrays.fill(result, coin);
        return result;
    }

    /**
     * Объединение двух массивов монет в один
     * @param first первый массив
     * @param second второй массив
     * @return результат объединения
     */
    private int[] merge(int[] first, int[] second) {
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * Проверяет корректность ввода значения для купюры
     * @param value купюра
     * @return true - если значение задано верно, false - в противном случае
     */
    private boolean validValue(int value) {
        boolean result = false;
        for (int banknote : this.banknotes) {
            if (banknote == value) {
                result = true;
                break;
            }
        }
        return result;
    }
}
