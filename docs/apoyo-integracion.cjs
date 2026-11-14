const fs=require('fs'),path=require('path'),assert=require('assert/strict');
const base='http://localhost:8081/api/v1';
async function login(username,password){const r=await fetch(base+'/auth/login',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({username,password})});const d=await r.json();assert(r.ok,JSON.stringify(d));return d.token;}
async function api(token,url,method='GET',body){const r=await fetch(base+url,{method,headers:{Authorization:'Bearer '+token,'Content-Type':'application/json'},body:body?JSON.stringify(body):undefined});const txt=await r.text();if(!r.ok)throw Error(r.status+' '+txt);return txt?JSON.parse(txt):null;}
module.exports={login,api,base};
