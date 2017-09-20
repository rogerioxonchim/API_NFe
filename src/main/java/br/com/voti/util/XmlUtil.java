package br.com.voti.util;

import br.com.voti.exception.NfeException;
import br.com.voti.xsd.consStatServ.TConsStatServ;
import br.com.voti.xsd.enviNFe.TEnviNFe;
import br.com.voti.xsd.enviNFe.TNfeProc;
import br.com.voti.xsd.enviNFe.TProtNFe;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.Normalizer;
import java.util.zip.GZIPInputStream;

public class XmlUtil {

    private static final String STATUS       = "TConsStatServ";
    private static final String SITUACAO_NFE = "TConsSitNFe";
    private static final String ENVIO_NFE    = "TEnviNFe";
    private static final String DIST_DFE     = "DistDFeInt";
    private static final String INUTILIZACAO = "TInutNFe";
    private static final String NFEPROC      = "TNfeProc";
    private static final String EVENTO       = "TEnvEvento";
    private static final String TPROCEVENTO  = "TProcEvento";
    private static final String TCONSRECINFE = "TConsReciNFe";
    private static final String TConsCad     = "TConsCad";

    private static final String TProtNFe    = "TProtNFe";
    private static final String TProtEnvi   = "br.inf.portalfiscal.nfe.schema_4.enviNFe.TProtNFe";
    private static final String TProtCons   = "br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe";
    private static final String TProtReci   = "br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe";
    /**
     * Transforma o String do XML em Objeto
     *
     * @param xml
     * @param classe
     * @return T
     */
    public static <T> T xmlToObject(String xml, Class<T> classe) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), classe).getValue();
    }
        /**
         * Transforma o Objeto em XML(String)
         *
         * @param obj
         * @return
         * @throws JAXBException
         * @throws NfeException
         */

        public static <T> String objectToXml(Object obj) throws JAXBException, NfeException {

            JAXBContext context = null;
            JAXBElement<?> element = null;

            switch (obj.getClass().getSimpleName()) {

                case STATUS:
                    context = JAXBContext.newInstance(TConsStatServ.class);
                    element = new  br.com.voti.xsd.consStatServ.ObjectFactory().createConsStatServ((TConsStatServ) obj);
                    break;

                default:
                    throw new NfeException("Objeto não mapeado no XmlUtil:" + obj.getClass().getSimpleName());
            }
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty("jaxb.encoding", "Unicode");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            StringWriter sw = new StringWriter();

            marshaller.marshal(element, sw);

            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(sw.toString());

            return replacesNfe(xml.toString());

        }

        public static String gZipToXml(byte[] conteudo) throws IOException {
            if (conteudo == null || conteudo.length == 0) {
                return "";
            }
            GZIPInputStream gis;
            gis = new GZIPInputStream(new ByteArrayInputStream(conteudo));
            BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
            String outStr = "";
            String line;
            while ((line = bf.readLine()) != null) {
                outStr += line;
            }

            return outStr;
        }

        public static String criaNfeProc(TEnviNFe enviNfe, Object retorno) throws JAXBException, NfeException {

            TNfeProc nfeProc = new TNfeProc();
            nfeProc.setVersao("4.00");
            nfeProc.setNFe(enviNfe.getNFe().get(0));
            String xml = XmlUtil.objectToXml(retorno);
            nfeProc.setProtNFe(XmlUtil.xmlToObject(xml, TProtNFe.class));

            String xmlFinal = XmlUtil.objectToXml(nfeProc);

            return xmlFinal;
        }

        public static String removeAcentos(String str) {

            str = str.replaceAll("\r", "");
            str = str.replaceAll("\t", "");
            str = str.replaceAll("\n", "");
            str = str.replaceAll("&", "E");
            str = str.replaceAll(">\\s+<", "><");
            CharSequence cs = new StringBuilder(str == null ? "" : str);
            return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        }

        public static String replacesNfe(String xml) {

            xml = xml.replaceAll("ns2:", "");
            xml = xml.replaceAll("<!\\[CDATA\\[<!\\[CDATA\\[", "<!\\[CDATA\\[");
            xml = xml.replaceAll("\\]\\]>\\]\\]>", "\\]\\]>");
            xml = xml.replaceAll("ns3:", "");
            xml = xml.replaceAll("&lt;", "<");
            xml = xml.replaceAll("&amp;", "&");
            xml = xml.replaceAll("&gt;", ">");
            xml = xml.replaceAll("<Signature>", "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
            xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
            xml = xml.replaceAll(" xmlns=\"\" xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "");

            return xml;

        }
    }

