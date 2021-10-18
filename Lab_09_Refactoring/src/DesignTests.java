import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DesignTests {

    /*
    The tests within this class check the design of the code. Please note that these tests are unusual
    and in development you would not usually write these types of tests.
     */

    @Test(timeout = 1000)
    public void requestHandlerSingletonTest() {
        try {
            // Check for the method 'getInstance()'
            Method getInstance = RequestHandler.class.getDeclaredMethod("getInstance");
            RequestHandler rh1 = (RequestHandler) getInstance.invoke(null);
            RequestHandler rh2 = (RequestHandler) getInstance.invoke(null);
            assertSame("Singleton design pattern incorrectly implemented", rh1, rh2);

            // Check that you cannot use the basic RequestHandler constructor (it is private)
            Constructor<RequestHandler> constructor = RequestHandler.class.getDeclaredConstructor();
            assertFalse("Constructor should be private if using singleton design pattern",
                    Modifier.isPublic(constructor.getModifiers()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("RequestHandler needs to be refactored with the singleton design pattern");
        }
    }

    @Test(timeout = 1000)
    public void quoteHandlerSingletonTest() {
        try {
            // Check for the method 'getInstance()'
            Method getInstance = QuoteHandler.class.getDeclaredMethod("getInstance");
            QuoteHandler rh1 = (QuoteHandler) getInstance.invoke(null);
            QuoteHandler rh2 = (QuoteHandler) getInstance.invoke(null);
            assertSame("Singleton design pattern incorrectly implemented", rh1, rh2);

            // Check that you cannot use the basic RequestHandler constructor (it is private)
            Constructor<QuoteHandler> constructor = QuoteHandler.class.getDeclaredConstructor();
            assertFalse("Constructor should be private if using singleton design pattern",
                    Modifier.isPublic(constructor.getModifiers()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("RequestHandler needs to be refactored with the singleton design pattern");
        }
    }

    @Test(timeout = 1000)
    public void usesQuoteFactoryTest() {
        // Track whether it is used in either QuoteHandler or RequestHandler
        boolean factoryInUse = false;

        // Check if inside RequestHandler
        try {
            Field quoteFactory = RequestHandler.class.getDeclaredField("quoteFactory");
            assertSame("class field quoteFactory of RequestHandler is not an instance of QuoteFactory",
                    quoteFactory.getType(), QuoteFactory.class);
            assertFalse("class field quoteFactory of RequestHandler should be private",
                    Modifier.isPublic(quoteFactory.getModifiers()));
            factoryInUse = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        // Only need to check if factory is not already used
        if (!factoryInUse) {
            // Check if inside QuoteHandler
            try {
                Field quoteFactory = QuoteHandler.class.getDeclaredField("quoteFactory");
                assertSame("class field quoteFactory of QuoteHandler is not an instance of QuoteFactory",
                        quoteFactory.getType(), QuoteFactory.class);
                assertFalse("class field quoteFactory of QuoteHandler should be private",
                        Modifier.isPublic(quoteFactory.getModifiers()));
                factoryInUse = true;
            } catch (Exception e) {
                // Ignore exception (usually bad practice, do not typically do this)
            }
        }

        // If the factory class is not in use, throw error
        if (!factoryInUse) {
            throw new AssertionError("class field quoteFactory of RequestHandler does not exist. Make use of the QuoteFactory class");
        }
    }

    @Test(timeout = 1000)
    public void privateQuoteHandlerFieldTest() {
        try {
            // Get field
            Field quoteHandler = RequestHandler.class.getDeclaredField("quoteHandler");

            // Ensure private
            assertFalse("class field quoteHandler of RequestHandler should not be public",
                    Modifier.isPublic(quoteHandler.getModifiers()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("RequestHandler needs class field quoteHandler");
        }
    }

    @Test(timeout = 1000)
    public void nonStaticQuoteStorageTest() {
        try {
            // Get field
            Field quoteStorage = QuoteHandler.class.getDeclaredField("quoteStorage");
            assertFalse("class field quoteStorage of QuoteHandler should be non-static",
                    Modifier.isStatic(quoteStorage.getModifiers()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("QuoteHandler needs class field quoteStorage");
        }
    }

    @Test(timeout = 1000)
    public void nonStaticQuoteHandlerMethodsTest() {
        try {
            // Get methods (expecting input type to be Quote!)
            Method get1 = QuoteHandler.class.getDeclaredMethod("get");
            Method get2 = QuoteHandler.class.getDeclaredMethod("get", Quote.QuoteType.class);
            Method add = QuoteHandler.class.getDeclaredMethod("add", Quote.class);

            // Check if any are static
            assertFalse(
                    "Method 'get' of QuoteHandler should not be static",
                    Modifier.isStatic(get1.getModifiers())
            );
            assertFalse(
                    "Method 'get with input quoteType' of QuoteHandler should not be static",
                    Modifier.isStatic(get2.getModifiers())
            );
            assertFalse(
                    "Method 'add' of QuoteHandler should not be static",
                    Modifier.isStatic(add.getModifiers())
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("QuoteHandler is missing a methods get and add with input types QuoteType and Quote respectively");
        }
    }

    @Test(timeout = 1000)
    public void getReferenceToStorageTest() {
        try {
            // Note that it is done like this so that it works with both static and non-static implementations.
            // Create a new instance of QuoteHandler as is necessary to access fields/methods regardless of static or non-static
            Constructor<QuoteHandler> quoteHandlerConstructor = QuoteHandler.class.getDeclaredConstructor();
            quoteHandlerConstructor.setAccessible(true);
            QuoteHandler quoteHandler = quoteHandlerConstructor.newInstance();

            // Call the get method of QuoteHandler
            Method get = quoteHandler.getClass().getMethod("get");
            Object quotes1;
            Object quotes2;
            if (Modifier.isStatic(get.getModifiers())) {
                quotes1 = get.invoke(null);
                quotes2 = get.invoke(null);
            } else {
                quotes1 = get.invoke(quoteHandler);
                quotes2 = get.invoke(quoteHandler);
            }

            assertNotSame(
                    "Method 'get' of QuoteHandler should return a copy and not a reference to the quoteStorage",
                    quotes1, quotes2
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("QuoteHandler is missing a 'get' method");
        }
    }

    @Test(timeout = 1000)
    public void invalidTokenizationTest() {
        // Provide a string that does not match any valid input
        assertThrows(
                "QuoteFactory tokenize method should throw an exception on string input that does not match a token type",
                Exception.class, () -> {
                    QuoteFactory quoteFactory = new QuoteFactory();
                    quoteFactory.tokenize("invalid");
        });

        assertThrows(
                "QuoteFactory tokenize method should throw an exception on null input",
                Exception.class, () -> {
                    QuoteFactory quoteFactory = new QuoteFactory();
                    quoteFactory.tokenize(null);
                });
    }

    @Test(timeout = 1000)
    public void authorDeletedTest() {
        // Check if the file exists in this package
        boolean classExistsInPackage = false;
        try {
            Class.forName("Author");
            classExistsInPackage = true;
        } catch (ClassNotFoundException e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (classExistsInPackage) {
            throw new AssertionError("Author.java file should be deleted (is unnecessary)");
        }
    }

    @Test(timeout = 1000)
    public void quoteAuthorField() {
        // Check if the field 'quoteAuthor' exists in Quote class
        boolean quoteAuthorFieldExists = false;
        try {
            Quote.class.getDeclaredField("quoteAuthor");
            quoteAuthorFieldExists = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (quoteAuthorFieldExists) {
            throw new AssertionError("Field quoteAuthor in Quote does not match specifications and should be removed");
        }
    }

    @Test(timeout = 1000)
    public void unnecessaryQuoteFactoryMethodsTest() {
        // Check if methods exist in QuoteFactory
        boolean unnecessaryMethodsExist = false;
        try {
            QuoteFactory.class.getDeclaredMethod("tokenizeString", String.class);
            unnecessaryMethodsExist = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (unnecessaryMethodsExist) {
            throw new AssertionError("Method 'tokenizeString' of QuoteFactory is unnecessary and should be removed");
        }

        try {
            QuoteFactory.class.getDeclaredMethod("isStringQuoteType", String.class, Quote.QuoteType.class);
            unnecessaryMethodsExist = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (unnecessaryMethodsExist) {
            throw new AssertionError("Method 'isStringQuoteType' of QuoteFactory should be removed");
        }
    }

    @Test(timeout = 1000)
    public void unnecessaryValidsFieldTest() {
        // Check if the field exists
        boolean fieldExists = false;
        try {
            RequestHandler.class.getDeclaredField("valids");
            fieldExists = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (fieldExists) {
            throw new AssertionError("Field 'valids' of RequestHandler should be removed (unnecessary upon using Quote class)");
        }
    }

    @Test(timeout = 1000)
    public void unnecessaryQuoteTypeFieldTest() {
        // Check if the field exists
        boolean fieldExists = false;
        try {
            QuoteHandler.class.getDeclaredField("quoteTypes");
            fieldExists = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (fieldExists) {
            throw new AssertionError("Field 'quoteType' of QuoteHandler should be removed (unnecessary upon using Quote class)");
        }
    }

    @Test(timeout = 1000)
    public void inputTypesUseQuoteInQuoteHandler() {
        // Check if input type is NOT abstracted to use Quote
        boolean abstractInputType = false;

        try {
            // Check input type
            QuoteHandler.class.getDeclaredMethod("get", Quote.QuoteType.class);
            abstractInputType = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (!abstractInputType) {
            throw new AssertionError("Method 'get' of QuoteHandler input type is not QuoteType");
        }

        abstractInputType = false;
        try {
            // Check input type
            QuoteHandler.class.getDeclaredMethod("add", Quote.class);
            abstractInputType = true;
        } catch (Exception e) {
            // Ignore exception (usually bad practice, do not typically do this)
        }

        if (!abstractInputType) {
            throw new AssertionError("Method 'add' of QuoteHandler input type is not Quote");
        }
    }
}