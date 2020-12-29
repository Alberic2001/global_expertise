-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 29 déc. 2020 à 20:24
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_commande`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `id_adresse` int(11) NOT NULL,
  `rue` varchar(30) NOT NULL,
  `quartier` varchar(30) NOT NULL,
  `ville` varchar(20) NOT NULL,
  `details` varchar(50) DEFAULT NULL,
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `adresse`
--

INSERT INTO `adresse` (`id_adresse`, `rue`, `quartier`, `ville`, `details`, `id_client`) VALUES
(6, 'Rue 17', 'Almadies', 'Dakar', 'Vers terrain Fass', 2),
(7, 'Rue 23', 'Médina', 'Dakar', 'Vers terrain Fass', 4),
(10, '17', 'Ouakam', 'Dakar', 'Non loin de Yum-Yum', 4),
(11, '23', 'Keur Massar', 'Dakar', 'Aucun', 2),
(13, '21', 'Mamelles', 'Dakar', 'Non loin de la pharmacie des mamelles', 4),
(14, '29', 'Médina', 'Dakar', 'Non loin d\'Auchan Gibraltar', 6),
(15, '', '', '', '', 9);

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `id_categorie` int(11) NOT NULL,
  `num_categorie` varchar(30) NOT NULL,
  `libelle` varchar(30) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id_categorie`, `num_categorie`, `libelle`, `description`) VALUES
(1, 'num_cat_1234567891231', 'Produits informatique', NULL),
(2, 'num_cat_0123456789103', 'Meubles de salon', NULL),
(3, 'num_cat_2470374762468', 'Cosmétiques', ''),
(5, 'num_cat_3051562129119', 'Electroménagers', ''),
(6, 'num_cat_2035535878770', 'Produits de Ménage', ''),
(8, 'num_cat_8354250846193', 'Jeux Videos', 'weavsdvwad advawdsv'),
(9, 'num_cat_3257353194466', 'Ustensiles cuisine', 'Ce sont des ustensiles de cuisine'),
(11, 'num_cat_0103022678940', 'Vêtements', 'Chemises, pantanlons, boxers etc...');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_commande` int(11) NOT NULL,
  `num_commande` varchar(30) NOT NULL,
  `statut` varchar(20) NOT NULL,
  `date_commande` varchar(30) NOT NULL,
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `num_commande`, `statut`, `date_commande`, `id_client`) VALUES
(2, 'num_cmd_@45#14jUh7', 'EN_ATTENTE', '12/12/2020', 2),
(3, 'num_cmd_@45#1W_4d1', 'EN_ATTENTE', '29/12/2020', 9);

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
  `id_facture` int(11) NOT NULL,
  `num_facture` varchar(30) NOT NULL,
  `date_facture` varchar(30) NOT NULL,
  `montant_facture` double NOT NULL,
  `statut_facture` varchar(20) NOT NULL,
  `id_commande` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(11) NOT NULL,
  `code` varchar(30) NOT NULL,
  `libelle` varchar(40) NOT NULL,
  `prix` double NOT NULL,
  `quantite` int(11) NOT NULL,
  `id_categorie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `code`, `libelle`, `prix`, `quantite`, `id_categorie`) VALUES
(1, 'code_0000000000', 'HP PROBOOK 450 G5', 450000, 12, 1),
(3, 'code_0478523159', 'PS5 Gold', 1530500, 2, 8),
(4, 'code_0178523159', 'ASUS ROG 2000', 650000, 3, 1),
(5, 'code_1456322607', 'Souris sans fil', 6500, 20, 1),
(6, 'code_1478523149', 'Nivea Pommade', 2400, 50, 3),
(7, 'code_2357890164', 'Machine à laver Sharp', 1350555, 4, 5);

-- --------------------------------------------------------

--
-- Structure de la table `produit_commande`
--

CREATE TABLE `produit_commande` (
  `id_produitcommande` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `id_commande` int(11) NOT NULL,
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit_commande`
--

INSERT INTO `produit_commande` (`id_produitcommande`, `id_produit`, `id_commande`, `id_client`) VALUES
(3, 3, 2, 2),
(4, 6, 2, 2),
(5, 7, 2, 2),
(6, 4, 3, 9),
(7, 5, 3, 9);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `num_client` varchar(30) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `matricule` varchar(20) DEFAULT NULL,
  `login` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `service` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id_user`, `nom`, `prenom`, `email`, `telephone`, `num_client`, `type`, `matricule`, `login`, `password`, `service`) VALUES
(2, 'SECK', 'Mouhamed Moustapha', 'medsoseck@gmail.com', '+221783189228', 'num_client_rwqT1hjUSE', 'CLIENT', NULL, NULL, NULL, NULL),
(3, 'ZIANGBE', 'Albéric', 'alziangbe20111@gmail.com', '+221786062976', NULL, 'EMPLOYE', 'mat_0123654789', 'alberic', 'alberic', 'Comptabilité'),
(4, 'CAMARA', 'Ibn Bachir Kader', 'kader.camara@ism.edu.sn', '+221772498744', 'num_client_MceAHUb6pG', 'CLIENT', NULL, NULL, NULL, NULL),
(6, 'DIANI', 'Mamoudou Sékou', 'mamoudou.diani@ism.edu.sn', '+221781403895', 'num_client_I5odbLuRR4', 'CLIENT', NULL, NULL, NULL, NULL),
(7, 'MAPALI YOYA', 'Yvan Romaric', 'espablo362@gmail.com', '+221784561239', NULL, 'EMPLOYE', 'mat_0123654728', 'yvan', 'yvan', 'Commercial'),
(8, 'MANFOUMBY', 'Anges P.', 'anges.manfoumby@ism.edu.sn', '+221774569512', NULL, 'EMPLOYE', 'mat_5893247561', 'anges', 'anges', 'Commercial'),
(9, 'GOAT', 'Rails', 'alberic.ziangbe@ism.edu.sn', '+221784562318', 'num_client_glBSYJ2sY2', 'CLIENT', NULL, NULL, NULL, NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`id_adresse`),
  ADD KEY `adresse_ibfk_1` (`id_client`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id_categorie`),
  ADD UNIQUE KEY `libelle` (`libelle`),
  ADD UNIQUE KEY `num_categorie` (`num_categorie`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `id_client` (`id_client`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`id_facture`),
  ADD KEY `id_commande` (`id_commande`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `id_categorie` (`id_categorie`);

--
-- Index pour la table `produit_commande`
--
ALTER TABLE `produit_commande`
  ADD PRIMARY KEY (`id_produitcommande`),
  ADD KEY `id_commande` (`id_commande`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_client` (`id_client`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `num_client` (`num_client`),
  ADD UNIQUE KEY `matricule` (`matricule`),
  ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `id_adresse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id_categorie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `id_facture` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `produit_commande`
--
ALTER TABLE `produit_commande`
  MODIFY `id_produitcommande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD CONSTRAINT `adresse_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `commande_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `user` (`id_user`);

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id_commande`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `produit_ibfk_1` FOREIGN KEY (`id_categorie`) REFERENCES `categorie` (`id_categorie`);

--
-- Contraintes pour la table `produit_commande`
--
ALTER TABLE `produit_commande`
  ADD CONSTRAINT `produit_commande_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id_commande`),
  ADD CONSTRAINT `produit_commande_ibfk_2` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`),
  ADD CONSTRAINT `produit_commande_ibfk_3` FOREIGN KEY (`id_client`) REFERENCES `user` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
