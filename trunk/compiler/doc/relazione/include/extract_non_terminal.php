\section{Grammatica}
\begin{itemize}
<?
$fname="/home/antonio/Progetti/cUml2Svg/cuml2svg/src/org/cuml2svg/compiler/cuml2svg.jj";

$lines=file($fname);

for($i=0;$i<sizeof($lines);$i++){
	if(preg_match("/^(\/\/\/)/",$lines[$i])){
		$t= str_replace("///","",$lines[$i]);
		$t=str_replace("{","\{",$t);
		$t=str_replace("}","\}",$t);
		
		$t=str_replace(" ","  ",$t);
		
//		while(preg_match("/[\_A-Z]{2,}/u",$t,$r)){
//			$t=str_replace($r[0]," \\textsc{".strtolower($r[0])."} ",$t);
//		}
//		foreach($r as $el){
//			$t=str_replace($el," \\textsc{".strtolower($el)."} ",$t);
//		}
		$t=str_replace("_","{\_}",$t);
//		$t=str_replace("[","\[",$t);
//		$t=str_replace("]","\]",$t);
		
//		$t=str_replace("(","((",$t);
//		$t=str_replace(")","))",$t);
		$t=str_replace("|"," \\textbar ",$t);
		
		$t=str_replace("->"," -\\textgreater ",$t);
		$txt.="\\item ".$t."\n ";
	}
}
echo $txt;
?>
\end{itemize}