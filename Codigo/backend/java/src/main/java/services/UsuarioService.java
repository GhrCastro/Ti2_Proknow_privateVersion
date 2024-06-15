package services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import dao.DAO;
import dao.UsuarioDao;
import dao.WalletDao;
import models.Moeda;
import models.UserBadge;
import models.Usuario;
import models.Wallet;

public class UsuarioService {
    private final UsuarioDao usuarioDao;
    private final WalletDao walletDao;

    public UsuarioService(DAO dao) {
        this.usuarioDao = dao.getJdbiContext().onDemand(UsuarioDao.class);
        this.walletDao = dao.getJdbiContext().onDemand(WalletDao.class);
        createTablesIfNotExists();
    }

    public void createTablesIfNotExists() {
        usuarioDao.createTable();
        usuarioDao.createUserBadgesTable();
    }

    public void addUsuario(Usuario usuario) {
        if (usuario.isValid()) {
            try {
                Wallet wallet = new Wallet(usuario.getId());
                Moeda pkw = new Moeda("PKW");

                wallet.deposit(pkw, BigDecimal.valueOf(150));
                walletDao.insertWallet(wallet.getWalletId(), usuario.getId());
                walletDao.insertWalletBalance(wallet.getWalletId(), pkw.getName(), BigDecimal.valueOf(150));

                usuarioDao.insert(usuario.getId(), usuario.getName(), usuario.getCpf(), usuario.getEmail(),
                        usuario.getSalary(), usuario.getCellNumber(), usuario.getPassword(), usuario.getExpenses(),
                        usuario.getRegDate(), wallet.getWalletId());

                // Recebe badge cadastro
                //usuarioDao.insertUserBadge(usuario.getId(), UUID.fromString("c081aab6-f162-49b4-b5b5-f5ba9b8e9214"));

            } catch (Exception e) {
                System.err.println("Error creating user and wallet: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to create user and wallet", e);
            }
        } else {
            throw new IllegalArgumentException("Dados do usuário inválidos");
        }
    }

    public Usuario getUsuarioById(UUID id) {
        return usuarioDao.findById(id);
    }

    public Usuario getUsuarioByEmail(String email) {
        return usuarioDao.findByEmail(email);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioDao.listUsuarios();
    }

    public void updateUsuario(UUID id, Usuario usuario) {
        if (usuario.isValid()) {
            Usuario existingUsuario = usuarioDao.findById(id);
            if (existingUsuario != null) {
                usuarioDao.update(id, usuario.getName(), usuario.getCpf(), usuario.getEmail(), usuario.getSalary(),
                        usuario.getCellNumber(), usuario.getPassword(), usuario.getExpenses(), usuario.getRegDate(),
                        usuario.getWallet().getOwnerId());
            } else {
                throw new IllegalArgumentException("Usuário não encontrado");
            }
        } else {
            throw new IllegalArgumentException("Dados do usuário inválidos");
        }
    }

    public void deleteUsuario(UUID id) {
        usuarioDao.delete(id);
    }

    // users-badges
    public void addUserBadge(UUID user_id, UUID badge_id) throws Exception {

        try {
            usuarioDao.insertUserBadge(user_id, badge_id);
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("Erro ao vincular badge ao usuário" + e);
        }
    }

    // retorna todas as badges que o usuario possui enabled
    public List<UserBadge> getAllUserBadges(UUID user_id) throws Exception {
        try {
            System.out.println("Fetching badges for user ID: " + user_id);
            List<UserBadge> badges = usuarioDao.listAllUsersBadges(user_id);
            System.out.println("Badges fetched successfully: " + badges);
            return badges;
        } catch (Exception e) {
            System.err.println("Error fetching badges: " + e.getMessage());
            e.printStackTrace(); // Mantém o stack trace original
            throw new Exception("Erro ao gerar lista de badges", e);
        }
    }

}
