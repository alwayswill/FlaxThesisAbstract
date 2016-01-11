package org.flax.thesis.main;

public final class Consts {
	public static final String APPROOTPATH = System.getProperty("user.dir");
	public static final String CACHEPATH = Consts.APPROOTPATH+"/cache";
	public static final String DBPATH = Consts.APPROOTPATH+"/db";
	public static final String EXPORTSPATH = Consts.APPROOTPATH+"/exports";
	public static final String TEMPLATESPATH = Consts.APPROOTPATH+"/templates";
	public static final String IDENTIFIERPATH = Consts.APPROOTPATH+"/cache"+"/identifiers";
	public static final String ABSTRACTPREFIX = "Abstract_";
	public static final String UNIFOLDERPREFIX = "Uni_";
	public static final String INDENTIFIERLISTFILENAME = "IndentifierList.xml";
	public static final String UNILISTFILE = "UniList.xml";
	
	
	public static final String UNILISTLINK = "http://simba.cs.uct.ac.za/~ethos/cgi-bin/OAI-XMLFile-2.21/XMLFile/ethos/oai.pl?verb=ListSets";
	
	public static final String IDENTIFIERSLIST = "http://simba.cs.uct.ac.za/~ethos/cgi-bin/OAI-XMLFile-2.21/XMLFile/ethos/oai.pl?verb=ListIdentifiers&metadataPrefix=oai_dc&set=";
	public static final String ABSTRACTLINK = "http://simba.cs.uct.ac.za/~ethos/cgi-bin/OAI-XMLFile-2.21/XMLFile/ethos/oai.pl?verb=GetRecord&metadataPrefix=uketd_dc&identifier=";
	  
	public static final int TIMEOUT = 600000;
	  
	  // PRIVATE //

	  /**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	  private Consts(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	  }
}
