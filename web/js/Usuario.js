/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Usuario
{
    static inicializar() {
        
        let elemConsultar = document.querySelector('#btnConsultar');
        elemConsultar.setAttribute('onclick', "Usuario.consultarServidor();");
        let elemInsertar = document.querySelector('#btnInsertar');
        elemInsertar.setAttribute('onclick', "Usuario.insertar();");
    }

    static consultarServidor()
    {
        const traemeUsuario = async () => {
            let respuesta =
                await fetch('./ServletCP',
                {"method":"GET"});
                let objRes = JSON.parse(
                        await respuesta.text()
                        );
                //document.querySelector('#panelResultado').innerHTML 
                        //+= objRes[0].usuario;
                        //= objRes[0]; 
                let usuarios = objRes;
                let textoPlantillaJS =
                document.querySelector('#plantillaJS').innerHTML;
                document.querySelector('#panelResultado').innerHTML += eval(textoPlantillaJS);

        };
        traemeUsuario().catch (ex => {
            document.querySelector('#panelResultado').innerHTML += "ERROR!!!" + ex.message;
        });
    }
    
    static insertar()
    {
        const traer = async() => {
            let usuario = {};
            usuario.nombre = document.querySelector("#usuario_nombre").value;
            usuario.email = document.querySelector("#usuario_email").value;
            usuario.apellido = document.querySelector("#usuario_apellido").value;
            usuario.clave = document.querySelector("#usuario_clave").value;
            usuario.usuario = document.querySelector("#usuario_usuario").value;
            usuario.activo = "1";
            let usuarioStringJSON = JSON.stringify(usuario);
            let respuesta = await fetch("./ServletCP",
                    {method: 'POST', body: usuarioStringJSON});
            let datotexto = JSON.parse(await respuesta.text());
            document.querySelector('#panelResultado').innerHTML = datotexto;
        };
        traer()
                .catch(ex => {
                    document.querySelector("#panelResultado").innerHTML = 'ERROR: ' + ex.message;
                });

    }
    
    
}

///////////////////////////////////////// main() // Ejecucion Inicial Default
Usuario.inicializar();
/////////////////////////////////////////

