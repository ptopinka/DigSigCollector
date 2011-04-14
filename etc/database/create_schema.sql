CREATE TABLE `t_signatures` (
  `idpodpis` int(11) NOT NULL auto_increment,
  `iddoc` int(11) NOT NULL,
  `CN` varchar(100) DEFAULT NULL,
  `login` varchar(100) DEFAULT NULL,
   isDeleted bool DEFAULT NULL,
  `blobsignature` blob,
  KEY `iddoc` (`iddoc`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 

CREATE TABLE `t_documents` (
  `docid` int(11) NOT NULL AUTO_INCREMENT,
  `docurl` varchar(100) NOT NULL,
  `validstart` date NOT NULL,
  `validend` date NOT NULL,
  `description` varchar(254) DEFAULT NULL,
  `filetype` varchar(5) NOT NULL,
  `fileblob` blob,
  `isDeleted` bool DEFAULT NULL,
  `hash` varchar(20) NOT NULL,
  `type` varchar(10) NOT NULL,
  `filename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`docid`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1

create table t_users ( iduser int(10) not null auto_increment, login varchar(20) not null, name varchar(20) not null, surname varchar(40) not null, key iduser(iduser))engine=MyISAM DEFAULT CHARSET=latin1;


create table t_sigstatus ( idstat int(11) NOT NULL AUTO_INCREMENT, statname varchar(20) NOT NULL, description varchar(100) NOT NULL, KEY idstat(idstat)) ENGINE=MyISAM DEFAULT CHARSET=latin1;


create table t_docsignuser( id int(11) not null auto_increment, primary key(id),docid int  not null, iduser int not null, idstatus int not null, foreign key(idstatus) references t_sigstatus(idstat) on update cascade on delete restrict, foreign key(iduser) references t_users(iduser) on update cascade on delete restrict ,  foreign key(docid) references t_documents(docid) on update cascade on delete restrict) engine=MyISAM DEFAULT CHARSET=latin1;


