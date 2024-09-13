package fr.rouen.mastergil.tptest;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class BankAccountTest {

    @Test
    public void creerCompte_shouldInitializeSoldeAttributeWithDefaultValues(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        //WHEN
        bankAccount.creerCompte();
        //THEN
        assertThat(bankAccount.solde).isNotNull();

    }

    @Test
    public void creerCompte_shouldInitializeSoldeAttributeWithParameters(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int montant = 2;
        Devise devise = Devise.DINAR;
        Money solde;
        //WHEN
        bankAccount.creerCompte(montant, devise);
        //THEN
        solde = bankAccount.solde;
        assertThat(solde).isNotNull();
        assertThat(solde.getMontant()).isEqualTo(montant);
        assertThat(solde.getDevise()).isEqualTo(devise);
    }

    @Test
    public void consulterSolde_shouldReturnDescriptionOfAccountSold(){
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        Money solde = new Money();
        solde.setMontant(2);
        solde.setDevise(Devise.DINAR);
        bankAccount.solde = solde;
        String description;
//        int montant = 2;
//        String soldeDeviseName = "DINAR";
        //WHEN
        description = bankAccount.consulterSolde();
        //THEN
        assertThat(description).isEqualTo("Votre solde actuel est de 2 DINAR");
    }

    @Test
    public void deposerArgent_shouldAddMontantToSoldAmount(){
        //GIVEN
        int oldAmount = 4;
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        bankAccount.solde.setMontant(oldAmount);
        int addedAmount = 2;
        //WHEN
        bankAccount.deposerArgent(addedAmount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo(6);
    }

    @Test
    public void retirerArgent_shouldDeduceMontantToSoldAmount(){
        //GIVEN
        int oldAmount = 4;
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        bankAccount.solde.setMontant(oldAmount);
        int deducedAmount = 2;
        //WHEN
        bankAccount.retirerArgent(deducedAmount);
        //THEN
        assertThat(bankAccount.solde.getMontant()).isEqualTo(2);
    }

    @Test
    public void isSoldePositif_shouldReturnTrueWhenSoldeIsPositif(){
        //GIVEN
        int amount = 2;
        boolean positif = false;
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        bankAccount.solde.setMontant(amount);
        //WHEN
        positif = bankAccount.isSoldePositif();
        //THEN
        assertThat(positif).isTrue();
    }

    @Test
    public void isSoldePositif_shouldReturnFalseWhenSoldeIsNegatif(){
        //GIVEN
        int amount = -2;
        boolean positif = true;
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        bankAccount.solde.setMontant(amount);
        //WHEN
        positif = bankAccount.isSoldePositif();
        //THEN
        assertThat(positif).isFalse();
    }

    @Test
    public void isSoldePositif_shouldReturnTrueWhenSoldeIs0(){
        //GIVEN
        int amount = 0;
        boolean positif = false;
        BankAccount bankAccount = new BankAccount();
        bankAccount.solde = new Money();
        bankAccount.solde.setMontant(amount);
        //WHEN
        positif = bankAccount.isSoldePositif();
        //THEN
        assertThat(positif).isTrue();
    }


}