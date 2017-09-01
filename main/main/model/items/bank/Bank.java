package main.model.items.bank;

import main.model.players.Client;

public class Bank {

	private Client c;
	private long lastSearch;

	public Bank(Client Client) {
		this.c = Client;
	}


	private BankTab[] bankTabs = { new BankTab(0), new BankTab(1), new BankTab(2), new BankTab(3), new BankTab(4), new BankTab(5), new BankTab(6), new BankTab(7), new BankTab(8) };

	public BankTab[] getBankTab() {
		return bankTabs;
	}

	public BankTab getBankTab(int tabId) {
		for (BankTab tab : bankTabs)
			if (tab.getTabId() == tabId)
				return tab;
		return bankTabs[0];
	}

	public void setBankTab(int tabId, BankTab tab) {
		this.bankTabs[tabId] = tab;
	}

	private BankTab currentTab = getBankTab()[0];

	public BankTab getCurrentBankTab() {
		if (currentTab == null)
			currentTab = getBankTab()[0];
		return this.currentTab;
	}

	public BankTab setCurrentBankTab(BankTab bankTab) {
		return this.currentTab = bankTab;
	}

	public long getLastSearch() {
		return lastSearch;
	}

	public void setLastSearch(long lastSearch) {
		this.lastSearch = lastSearch;
	}

	public void deleteAll() {
		for (BankTab tab : bankTabs) {
			if (tab == null) {
				continue;
			}
			if (tab.size() > 0) {
				tab.bankItems.clear();
			}
		}
	}

}
