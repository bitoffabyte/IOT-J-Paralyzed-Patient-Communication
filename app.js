var express=require("express");
var app=express();
var bodyParser=require("body-parser");
var mongoose=require("mongoose");
var passport=require("passport");
var User=require("./models/user");
var LocalStatergy=require("passport-local");
var passportLocalMongoose=require("passport-local-mongoose");
const { initialize, session } = require("passport");

mongoose.connect('mongodb://localhost:27017/user', {
  useNewUrlParser: true,
  useUnifiedTopology: true
})
.then(() => console.log('Connected to DB!'))
.catch(error => console.log(error.message));

app.use(bodyParser.urlencoded({extended:true}));
app.set("view engine","ejs");
app.use(require("express-session")({
    secret:"IoT Blink detection",
    resave:false,
    saveUninitialized:false
}));
app.use(passport.initialize());
app.use(passport.session());

passport.use(new LocalStatergy(User.authenticate()));
passport.serializeUser(User.serializeUser());
passport.deserializeUser(User.deserializeUser());

//SCHEMA
var patientdetailsSchema = new mongoose.Schema({
    image: String,
    Name: String,
    DOB: String,
    Age: Number,
    Contact: Number,
    Address: String,
    Message: Array
});

var patientdetails = mongoose.model("patientdetails",patientdetailsSchema);

patientdetails.create({
    image: "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/220px-User_icon_2.svg.png",
    Name: "Rajendar P",
    DOB: "1936-09-08",
    Age: 84,
    Contact: 9962562252,
    Address: "Coimbatore",
    Message: "Hello"
},function(err,patient){
    if(err){
        console.log(err);
    }
    else{
        console.log("NEWLY CREATED");
        console.log(patient);
    }
});


app.get("/",function(req,res){
    res.render("landing");
});

app.get("/patients",function(req,res){
    patientdetails.find({},function(err, patients){
        if(err){
            console.log(err);
        }
        else{
            res.render("patients",{patients:patients});
        }
    });
});

/*app.get("/home",function(req,res){
    res.render("home");
});*/

app.get("/secret/:id",function(req,res){
    patientdetails.findById(req.params.id,function(err,foundpatient){
       if(err){
           console.log("something gone wrong");
       }
       else{
           res.render("secret",{patient:foundpatient});
       }
    });
});

/*app.get("/register",function(req,res){
    res.render("register");
});
app.post("/register",function(req,res){
    User.register(new User({username: req.body.username}), req.body.password, function(err,user){
        if(err){
            console.log(err);
            return res.render("register");
        }
        passport.authenticate("local")(req,res,function(){
            res.redirect("/secret/:id");
        });
    });
});*/

/*app.get("/login",function(req,res){
    res.render("login");
});
app.post("/login",passport.authenticate("local",{
    successRedirect:"/secret/:id",
    failureRedirect:"/login"
}),function(req,res){
});

app.get("/logout",function(req,res){
    req.logout();
    res.redirect("/");
})*/

app.get("/patients/:id",function(req,res){
    patientdetails.findById(req.params.id,function(err,foundpatient){
       if(err){
           console.log("something gone wrong");
       }
       else{
           res.render("show",{patient:foundpatient});
       }
    });
});

/*function isLoggedIn(req, res, next){
    if(req.isAuthenticated()){
        return next();
    }
    res.redirect("/login");
}*/

app.listen(3000, function(){
    console.log("Server has started");
});
