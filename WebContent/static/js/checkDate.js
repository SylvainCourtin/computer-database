/**
 * 
 */

function compare()
{
    var introduced = document.getElementById("introduced").value;
    var discontinued = document.getElementById("discontinued").value;
    
    try {
	    if(introduced != null && discontinued != null && (new Date(introduced).getTime() > new Date(discontinued).getTime()) )
	    {
	    	$("#act").attr("disabled",true);
	    	$("#wrongOrderDate").show();
	    }
	    else
	   	{
	    	$("#act").attr("disabled",false);
	    	$("#wrongOrderDate").hide();
	   	}
    }
    catch(err)
    {
    	$("#act").attr("disabled",true);
    }
    
}