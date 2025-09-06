package cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return this.accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return this.accounts.put(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        this.accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        var account = this.accounts.get(id);
        return Optional.ofNullable(account);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (amount <= 0 || getById(fromId).isEmpty() || getById(toId).isEmpty()
                || getById(fromId).get().amount() < amount) {
            return false;
        }
        Account from = getById(fromId).get();
        Account to = getById(toId).get();
        Account newFrom = new Account(from.id(), from.amount() - amount);
        Account newTo = new Account(to.id(), to.amount() + amount);
        return update(newFrom) && update(newTo);
    }
}
