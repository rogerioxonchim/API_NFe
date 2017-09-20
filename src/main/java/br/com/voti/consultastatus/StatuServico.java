package br.com.voti.consultastatus;

import br.com.voti.exception.NfeException;
import br.com.voti.util.XmlUtil;
import br.com.voti.wsdl.nfestatusservico.NfeStatusServico4Stub;
import br.com.voti.xsd.consStatServ.TConsStatServ;
import br.com.voti.xsd.retConsStatServ.TRetConsStatServ;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.rmi.RemoteException;

public class StatuServico {

    public static TRetConsStatServ consultStatus(TConsStatServ consStatServ) throws NfeException {

        try {
            String url = "https://homologacao.nfe.fazenda.sp.gov.br/ws/nfestatusservico4.asmx";
            String xml = XmlUtil.objectToXml(consStatServ);

            System.out.println("Xml Status: " + xml);
            OMElement ome = AXIOMUtil.stringToOM(xml);

            NfeStatusServico4Stub.NfeDadosMsg dadosMsg = new NfeStatusServico4Stub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NfeStatusServico4Stub stub = new NfeStatusServico4Stub(url);
            NfeStatusServico4Stub.NfeResultMsg result = stub.nfeStatusServicoNF(dadosMsg);

            return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetConsStatServ.class);

        } catch (RemoteException | XMLStreamException | JAXBException e) {
            throw new NfeException(e.getMessage());
        }
    }
}
