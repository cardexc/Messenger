package adevteam.messenger.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import adevteam.messenger.R;

public class ContactsFragment extends Fragment {

    private ListView contactsList;

    public static ContactsFragment newInstance() {

        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;

    }

    public ContactsFragment() {}

   @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        //TODO replace "null" statement to sqilte database cursor
        ContactsListCursorAdapter contactsListCursorAdapter = new ContactsListCursorAdapter(getActivity(), null);

        contactsList = (ListView) view.findViewById(R.id.fragmentContacts_list);
        contactsList.setAdapter(contactsListCursorAdapter);

        return view;
    }

    private class ContactsListCursorAdapter extends CursorAdapter {

        public ContactsListCursorAdapter(Context context, Cursor c) {
            super(context, c, FLAG_AUTO_REQUERY);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.fragment_contacts_listitem, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            //TODO replace string representation of column
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

            //TODO replace string representation of column
            String imagepath = cursor.getString(cursor.getColumnIndexOrThrow("imagepath"));

            ((TextView) (view.findViewById(R.id.fragmentContacts_listitem_image))).setText(name);
            setImageToView((ImageView)(view.findViewById(R.id.fragmentContacts_listitem_image)),imagepath);

        }

        public void setImageToView(ImageView contact_image, String path) {

            File accPicFile = new File(path);
            if (!accPicFile.exists()) {
                contact_image.setImageResource(R.drawable.anonymous_contact);
            } else {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferQualityOverSpeed = true;

                Bitmap accountPicture = BitmapFactory.decodeFile(accPicFile.getAbsolutePath(), options);
                contact_image.setImageBitmap(accountPicture);

            }
        }

    }

}
