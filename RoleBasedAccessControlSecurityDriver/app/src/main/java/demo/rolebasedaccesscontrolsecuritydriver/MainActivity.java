package demo.rolebasedaccesscontrolsecuritydriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import demo.rbac.library.*;
import demo.rbac.library.operations.*;

public class MainActivity extends AppCompatActivity {

    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTest = (Button)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("*************** DRIVER APPLICATION (MAIN) : START ******************");
                runFullDemo();
                System.out.println("***************  DRIVER APPLICATION (MAIN) : END  ******************");
            }
        });
    }

    private void runFullDemo() {

        System.out.println(">>> createDataUsingStringValues() fired");

        System.out.println(">>> **************** START OF DEMO *****************");
        System.out.println(">>> **************** START OF DEMO *****************");
        System.out.println(">>> **************** START OF DEMO *****************");
        System.out.println(" ");

        //*************************
        // Input Data
        //*************************

        operations.addWebPage("news.html");
        operations.addWebPage("weather.html");
        operations.addWebPage("sports.html");
        System.out.println(">>> web pages created");
        operations.printData();

        operations.addRole("Creator");
        operations.addRole("Viewer");
        System.out.println(">>> roles created");
        operations.printData();

        operations.addUser("Andy");
        operations.addUser("Mitch");
        System.out.println(">>> users created");
        operations.printData();

        operations.assignRoleToUser("Andy", "Creator");
        operations.assignRoleToUser("Mitch", "Viewer");
        System.out.println(">>> users created");
        operations.printData();

        //*************************
        // Permissions
        //*************************
        operations.definePermission("Creator", "news.html", true);
        operations.definePermission("Creator", "weather.html", true);
        operations.definePermission("Creator", "sports.html", true);

        operations.definePermission("Viewer", "news.html", false);
        operations.definePermission("Viewer", "weather.html", false);
        operations.definePermission("Viewer", "sports.html", false);

        System.out.println(">>> permissions created");
        operations.printData();

        //*************************
        // Operation
        //*************************

        operations.queryUserForURL("Andy", "news.html");
        operations.queryUserForURL("Andy", "weather.html");
        operations.queryUserForURL("Andy", "sports.html");

        operations.queryUserForURL("Mitch", "news.html");
        operations.queryUserForURL("Mitch", "weather.html");
        operations.queryUserForURL("Mitch", "sports.html");

        operations.queryUserForURL("Craig", "news.html");
        operations.queryUserForURL("Philippe", "business.html");
        operations.queryUserForURL("Andy", "entertainment.html");

        //*************************
        // Removals
        //*************************

        operations.deleteUser("Mitch");
        operations.printData();

        operations.deleteWebPage("sports.html");
        operations.printData();

        operations.deleteWebPage("entertainment.html");
        operations.printData();

        operations.deletePermission("Viewer", "sports.html");
        operations.printData();

        operations.deletePermission("Creator", "editorial.html");
        operations.printData();

        operations.deleteRole("Viewer");
        operations.printData();

        operations.deleteUser("Craig");
        operations.printData();

        operations.deleteUser("Andy");
        operations.printData();

        operations.deleteRole("Management");
        operations.printData();

        System.out.println(" ");
        System.out.println(">>> **************** END OF DEMO *****************");
        System.out.println(">>> **************** END OF DEMO *****************");
        System.out.println(">>> **************** END OF DEMO *****************");

        Toast.makeText(this, "See the Debug Log (ADB LOGCAT) for messages generated.", Toast.LENGTH_LONG).show();

    }

}