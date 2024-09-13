package fr.rouen.mastergil.tptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class BankAccountWithDAOTest {

    @Mock
    JdbcDAO bankDaoMock;
    @Mock
    Connection connectionMock;
    @InjectMocks
    BankAccountWithDAO bankAccountWithDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        when(bankDaoMock.setUpConnection()).thenReturn(connectionMock);

//        doNothing().when(connectionMock).setAutoCommit(true);
        lenient().when(connectionMock.isReadOnly()).thenReturn(false);
        lenient().when(connectionMock.isClosed()).thenReturn(false);
    }

    @Test
    public void creerCompte_shouldConnectToBddAndCreateAnAccountWithDefaultValuesWhenSucces() throws SQLException, ConnectException {
        //WHEN
        bankAccountWithDAO.creerCompte();
        //THEN
        verify(bankDaoMock, times(1)).setUpConnection();
        verify(connectionMock, times(1)).setAutoCommit(true);
        verify(bankDaoMock, times(1)).creerCompte();
    }

     @Test
    public void creerCompte_shouldThrowConnectExceptionWhenConnectionIsReadOnlyOrIsClosed() throws SQLException, ConnectException {
        //GIVEN
        when(connectionMock.isReadOnly()).thenReturn(true);

        //THEN
        assertThatExceptionOfType(ConnectException.class)
                .isThrownBy(() ->  bankAccountWithDAO.creerCompte());
    }

    @Test
    public void creerCompte_shouldThrowAnExceptionWhenSetupConnectionThrowsConnectException1() throws SQLException, ConnectException {
        //GIVEN
        when(connectionMock.isReadOnly()).thenReturn(true);

        //THEN
        assertThatThrownBy(() -> bankAccountWithDAO.creerCompte())
                .isInstanceOf(ConnectException.class);
    }

    @Test
    public void creerCompte_shouldThrowAConnectExceptionWhenSetupConnectionThrowsConnectException2() throws SQLException, ConnectException {
        //GIVEN
        when(connectionMock.isClosed()).thenReturn(true);

        //THEN
        assertThatThrownBy(() -> bankAccountWithDAO.creerCompte())
                .isInstanceOf(ConnectException.class);
    }

    @Test
    public void creerCompte_shouldThrowAnSQLExceptionWhensetupConnectionThrowsOne() throws SQLException {
        //GIVEN
        doThrow(SQLException.class).when(connectionMock).setAutoCommit(true);

        //THEN
        assertThatThrownBy(() -> bankAccountWithDAO.creerCompte())
                .isInstanceOf(SQLException.class);

    }

    @Test
    public void creerCompte_shouldConnectToBddAndCreateAnAccountWithGivenValuesWhenSucces() throws SQLException, ConnectException {
        //GIVEN
        int montant = 4;
        Devise devise = Devise.DINAR;
        //WHEN
        bankAccountWithDAO.creerCompte(montant, devise);
        //THEN
        verify(bankDaoMock, times(1)).setUpConnection();
        verify(connectionMock, times(1)).setAutoCommit(true);
        verify(bankDaoMock, times(1)).creerCompte(montant, devise);
    }

    @Test
    public void creerCompteWithParameters_shouldThrowAnExceptionWhenSetupConnectionThrowsConnectException1() throws SQLException, ConnectException {
        //GIVEN
        int montant = 4;
        Devise devise = Devise.DINAR;
        when(connectionMock.isReadOnly()).thenReturn(true);

        //THEN
        assertThatThrownBy(() -> bankAccountWithDAO.creerCompte(montant,devise))
                .isInstanceOf(ConnectException.class);
    }


    @Test
    public void creerCompteWithParameters_shouldThrowAnSQLExceptionWhensetupConnectionThrowsOne() throws SQLException {
        //GIVEN
        int montant = 4;
        Devise devise = Devise.DINAR;
        doThrow(SQLException.class).when(connectionMock).setAutoCommit(true);

        //THEN
        assertThatThrownBy(() -> bankAccountWithDAO.creerCompte(montant, devise))
                .isInstanceOf(SQLException.class);

    }

    @Test
    public void consulterSolde_shouldReturnTheSoldWithMessageFormatWhenSuccess() throws SQLException, ConnectException {
        //GIVEN
        Money solde = new Money(100, Devise.EURO);
        String expectedResult = MessageFormat.format( "Votre solde actuel est de {0} {1}", solde.getMontant(), solde.getDevise().name());
        when(bankDaoMock.getSolde()).thenReturn(solde);


        //WHEN
       String result = bankAccountWithDAO.consulterSolde();

        //THEN
        assertThat(result).isEqualTo(expectedResult);

    }

      @Test
    public void deposerArgent_shouldReturnTheNewSold() throws SQLException, ConnectException {
        //GIVEN
        int addedAmount = 4;
        Money solde = new Money(7, Devise.DINAR);
        when(bankDaoMock.getSolde()).thenReturn(solde);

        //WHEN
        Money actualSold = bankAccountWithDAO.deposerArgent(addedAmount);

        //THEN
        verify(bankDaoMock, times(1)).setUpConnection();
        verify(connectionMock, times(1)).setAutoCommit(true);
        verify(bankDaoMock, times(1)).getSolde();
        verify(bankDaoMock, times(1)).saveMoney(solde);
        assertThat(actualSold.getMontant()).isEqualTo(11);

    }

    @Test
    public void isSoldePositif_shouldReturnTrueWhenSoldIsPositif() throws SQLException, ConnectException {
        //GIVEN
        Money solde = new Money(7, Devise.DINAR);
        when(bankDaoMock.getSolde()).thenReturn(solde);


        //WHEN
        Boolean result = bankAccountWithDAO.isSoldePositif();

        //THEN
        verify(bankDaoMock, times(1)).setUpConnection();
        verify(connectionMock, times(1)).setAutoCommit(true);
        verify(bankDaoMock, times(1)).getSolde();
        assertThat(result).isTrue();

    }

}