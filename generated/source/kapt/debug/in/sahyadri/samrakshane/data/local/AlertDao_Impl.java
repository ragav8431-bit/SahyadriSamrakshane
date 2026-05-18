package in.sahyadri.samrakshane.data.local;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class AlertDao_Impl implements AlertDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<AlertEntity> __insertAdapterOfAlertEntity;

  public AlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfAlertEntity = new EntityInsertAdapter<AlertEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `alerts` (`id`,`type`,`status`,`description`,`aiSuggestion`,`photoPath`,`latitude`,`longitude`,`createdAtMillis`,`synced`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getType() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getType());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getStatus());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getDescription());
        }
        if (entity.getAiSuggestion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getAiSuggestion());
        }
        if (entity.getPhotoPath() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getPhotoPath());
        }
        statement.bindDouble(7, entity.getLatitude());
        statement.bindDouble(8, entity.getLongitude());
        statement.bindLong(9, entity.getCreatedAtMillis());
        final int _tmp = entity.getSynced() ? 1 : 0;
        statement.bindLong(10, _tmp);
      }
    };
  }

  @Override
  public Object upsert(final AlertEntity alert, final Continuation<? super Unit> $completion) {
    if (alert == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfAlertEntity.insert(_connection, alert);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<AlertEntity>> observeAlerts() {
    final String _sql = "SELECT * FROM alerts ORDER BY createdAtMillis DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"alerts"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfAiSuggestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "aiSuggestion");
        final int _columnIndexOfPhotoPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "photoPath");
        final int _columnIndexOfLatitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "latitude");
        final int _columnIndexOfLongitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longitude");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfSynced = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "synced");
        final List<AlertEntity> _result = new ArrayList<AlertEntity>();
        while (_stmt.step()) {
          final AlertEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpAiSuggestion;
          if (_stmt.isNull(_columnIndexOfAiSuggestion)) {
            _tmpAiSuggestion = null;
          } else {
            _tmpAiSuggestion = _stmt.getText(_columnIndexOfAiSuggestion);
          }
          final String _tmpPhotoPath;
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null;
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath);
          }
          final double _tmpLatitude;
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude);
          final double _tmpLongitude;
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude);
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final boolean _tmpSynced;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfSynced));
          _tmpSynced = _tmp != 0;
          _item = new AlertEntity(_tmpId,_tmpType,_tmpStatus,_tmpDescription,_tmpAiSuggestion,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpCreatedAtMillis,_tmpSynced);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object pendingAlerts(final Continuation<? super List<AlertEntity>> $completion) {
    final String _sql = "SELECT * FROM alerts WHERE synced = 0 ORDER BY createdAtMillis ASC";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfType = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "type");
        final int _columnIndexOfStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "status");
        final int _columnIndexOfDescription = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "description");
        final int _columnIndexOfAiSuggestion = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "aiSuggestion");
        final int _columnIndexOfPhotoPath = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "photoPath");
        final int _columnIndexOfLatitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "latitude");
        final int _columnIndexOfLongitude = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "longitude");
        final int _columnIndexOfCreatedAtMillis = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "createdAtMillis");
        final int _columnIndexOfSynced = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "synced");
        final List<AlertEntity> _result = new ArrayList<AlertEntity>();
        while (_stmt.step()) {
          final AlertEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpType;
          if (_stmt.isNull(_columnIndexOfType)) {
            _tmpType = null;
          } else {
            _tmpType = _stmt.getText(_columnIndexOfType);
          }
          final String _tmpStatus;
          if (_stmt.isNull(_columnIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _stmt.getText(_columnIndexOfStatus);
          }
          final String _tmpDescription;
          if (_stmt.isNull(_columnIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _stmt.getText(_columnIndexOfDescription);
          }
          final String _tmpAiSuggestion;
          if (_stmt.isNull(_columnIndexOfAiSuggestion)) {
            _tmpAiSuggestion = null;
          } else {
            _tmpAiSuggestion = _stmt.getText(_columnIndexOfAiSuggestion);
          }
          final String _tmpPhotoPath;
          if (_stmt.isNull(_columnIndexOfPhotoPath)) {
            _tmpPhotoPath = null;
          } else {
            _tmpPhotoPath = _stmt.getText(_columnIndexOfPhotoPath);
          }
          final double _tmpLatitude;
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude);
          final double _tmpLongitude;
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude);
          final long _tmpCreatedAtMillis;
          _tmpCreatedAtMillis = _stmt.getLong(_columnIndexOfCreatedAtMillis);
          final boolean _tmpSynced;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfSynced));
          _tmpSynced = _tmp != 0;
          _item = new AlertEntity(_tmpId,_tmpType,_tmpStatus,_tmpDescription,_tmpAiSuggestion,_tmpPhotoPath,_tmpLatitude,_tmpLongitude,_tmpCreatedAtMillis,_tmpSynced);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
